package fptu.capstone.gymmanagesystem.ui.gymClass

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.component.TextField
import fptu.capstone.gymmanagesystem.ui.component.shimmerLoadingAnimation
import fptu.capstone.gymmanagesystem.ui.gymClass.component.Communication
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.viewmodel.CommunicationViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CommunicationContent(
    modifier: Modifier,
    classId: String,
    communicationViewModel: CommunicationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val communicationState by communicationViewModel.communication.collectAsState()
    val isCommenting by communicationViewModel.isCommenting.collectAsState()
//    val isPosting by communicationViewModel.isPosting.collectAsState()
    val comment by communicationViewModel.comment.collectAsState()
    val communicationContent by communicationViewModel.communicationContent.collectAsState()
    val selectedCommunication by communicationViewModel.selectedCommunication.collectAsState()
    val sendCommentState by communicationViewModel.sendComment.collectAsState()
    val sendCommunicationState by communicationViewModel.sendCommunication.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    var showSelectImageDialog by remember { mutableStateOf(false) }
    val bitmap by communicationViewModel.imageBitmap.collectAsState()

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val galleryPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(permission = Manifest.permission.READ_MEDIA_IMAGES)
    } else {
        rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
    }


    val launcherTakePhoto =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            communicationViewModel.setImageBitmap(it)
            communicationViewModel.setCommunicationImage(saveBitmapToFile(it))
        }

    val launcherPickImage =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            communicationViewModel.setImageBitmap(it?.let { getBitmapFromUri(context, it) })
            communicationViewModel.setCommunicationImage(saveBitmapToFile(it?.let {
                getBitmapFromUri(
                    context,
                    it
                )
            }))
        }

//    LaunchedEffect(Unit) {
//        communicationViewModel.fetchCommunications(
//            FilterRequestBody(
//                orderBy = "CreateAt",
//                isAscending = true
//            )
//        )
//    }

    LaunchedEffect(key1 = sendCommunicationState) {
        if (sendCommunicationState is DataState.Success) {
            communicationViewModel.fetchCommunications(
                FilterRequestBody(
                    orderBy = "CreateAt",
                    isAscending = false
                )
            )
            Toast.makeText(context, "Post success", Toast.LENGTH_SHORT).show()
            communicationViewModel.clearAllState()
        } else if (sendCommunicationState is DataState.Error) {
            Toast.makeText(context, "Post failed", Toast.LENGTH_SHORT).show()
            communicationViewModel.clearAllState()
        }
    }

    LaunchedEffect(key1 = sendCommentState) {
        if (sendCommentState is DataState.Success) {
            communicationViewModel.setIsReplying()
            communicationViewModel.fetchCommunications(
                FilterRequestBody(
                    orderBy = "CreateAt",
                    isAscending = false
                )
            )
            Toast.makeText(context, "Comment sent", Toast.LENGTH_SHORT).show()
            communicationViewModel.clearAllState()
        }
        if (sendCommentState is DataState.Error) {
            Toast.makeText(context, "Comment failed", Toast.LENGTH_SHORT).show()
            communicationViewModel.clearAllState()
        }

    }

    when (communicationState) {
        is DataState.Loading -> {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .shimmerLoadingAnimation()
                )
                Gap.k16.Height()
                Box(
                    modifier = Modifier
                        .padding(start = 32.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .shimmerLoadingAnimation()
                ) {
                }
            }
        }

        is DataState.Success -> {
            val communications = (communicationState as DataState.Success).data.data
            SwipeRefresh(modifier = modifier, state = swipeRefreshState, onRefresh = {
                isRefreshing = true
                communicationViewModel.fetchCommunications(
                    FilterRequestBody(
                        orderBy = "CreateAt",
                        isAscending = false
                    )
                )
                isRefreshing = false
            }) {
                if (communications.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No communication yet")
                    }
                } else
                    LazyColumn(
                        modifier = modifier,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(communications) { communication ->
                            Communication(communication, onReply = {
                                communicationViewModel.setIsReplying()
                                communicationViewModel.setSelectedCommunication(communication)
                            }, onUnReply = {
                                communicationViewModel.setIsReplying()
                            })
                        }
                    }
            }
            Gap.k16.Height()
            Column(
                modifier = Modifier
                    .padding(bottom = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    bitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "Image",
                            modifier = Modifier
                                .height(70.dp)
                                .aspectRatio(3 / 4f)
                        )
                    }
                    Gap.k16.Width()
                    if (bitmap != null) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_cancel_24),
                            contentDescription = "Cancel",
                            modifier = Modifier.clickable {
                                communicationViewModel.setImageBitmap(null)
                                communicationViewModel.setCommunicationImage(null)
                            },
                            tint = Color.Gray
                        )
                    }
                }
                Gap.k8.Height()
                TextField(
                    label = "Write a something...",
                    value = if (isCommenting) comment else communicationContent,
                    maxLines = 3,
                    onTextChange = {
                        if (isCommenting) communicationViewModel.setComment(it) else communicationViewModel.setCommunicationContent(
                            it
                        )
                    },
                    prefix = {
                        if (!isCommenting)

                            Icon(
                                modifier = Modifier.padding(end = 16.dp).clickable {
                                    showSelectImageDialog = true
                                },
                                painter = painterResource(id = R.drawable.round_photo_camera_24),
                                contentDescription = "Image",
                            )

                    },
                    suffix = {
                        when (sendCommentState) {
                            is DataState.Loading -> {
                                CircularProgressIndicator()
                            }

                            else -> {
                                Icon(
                                    modifier = Modifier.padding(start = 16.dp).clickable {
                                        if (isCommenting && comment.isNotEmpty())
                                            communicationViewModel.sendComment(
                                                communicationId = selectedCommunication?.id!!
                                            )
                                        else if (communicationContent.isNotEmpty())
                                            communicationViewModel.postCommunication(
                                                classId = classId,
                                            )
                                        else {
                                            Toast.makeText(
                                                context,
                                                "Please write something",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    },
                                    painter = painterResource(id = R.drawable.round_send_24),
                                    contentDescription = "Send Comment",
                                    tint = if (communicationContent.isNotEmpty() || comment.isNotEmpty()) MaterialTheme.colorScheme.primary else Color.Gray
                                )

                            }
                        }
                    }
                )

            }

            if (showSelectImageDialog) {
                var takePhotoPending by remember { mutableStateOf(false) }
                var pickImagePending by remember { mutableStateOf(false) }

                if (takePhotoPending && cameraPermissionState.status.isGranted) {
                    LaunchedEffect(Unit) {
                        takePhotoPending = false
                        launcherTakePhoto.launch()
                    }
                }

                if (pickImagePending && galleryPermissionState.status.isGranted) {
                    LaunchedEffect(Unit) {
                        pickImagePending = false
                        launcherPickImage.launch("image/*")
                    }
                }
                AlertDialog(
                    onDismissRequest = { showSelectImageDialog = false },
                    confirmButton = { /*TODO*/ },
                    dismissButton = { Text(text = "Cancel") },
                    text = {
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .clip(shape = RoundedCornerShape(16.dp))
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                                    .clickable(onClick = {
                                        showSelectImageDialog = false

                                        if (cameraPermissionState.status.isGranted)
                                            launcherTakePhoto.launch()
                                        else {
                                            takePhotoPending = true
                                            cameraPermissionState.launchPermissionRequest()
                                        }
                                    }),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.round_photo_camera_24),
                                        contentDescription = "Take Photo"
                                    )
                                    Gap.k16.Width()
                                    Text(text = "Take a photo")
                                }
                            }
                            Gap.k16.Height()
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .clip(shape = RoundedCornerShape(16.dp))
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                                    .clickable(onClick = {
                                        showSelectImageDialog = false
                                        if (galleryPermissionState.status.isGranted)
                                            launcherPickImage.launch("image/*")
                                        else {
                                            pickImagePending = true
                                            galleryPermissionState.launchPermissionRequest()
                                        }
                                    }),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.round_folder_24),
                                        contentDescription = "Choose from Gallery"
                                    )
                                    Gap.k16.Width()
                                    Text(text = "Choose from Gallery")
                                }
                            }
                        }

                    })
            }
        }

        is DataState.Error -> {
            Text(text = (communicationState as DataState.Error).message)
        }

        else -> {}
    }
}

fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun saveBitmapToFile(bitmap: Bitmap?): File? {
    val file = File.createTempFile("communication", ".jpg")
    try {
        val outputStream = FileOutputStream(file)
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
    return file
}

