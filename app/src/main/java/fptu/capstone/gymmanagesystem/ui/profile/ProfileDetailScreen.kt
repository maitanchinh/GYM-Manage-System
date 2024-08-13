@file:OptIn(ExperimentalMaterial3Api::class)

package fptu.capstone.gymmanagesystem.ui.profile

import android.Manifest
import android.app.DatePickerDialog
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.component.LargeButton
import fptu.capstone.gymmanagesystem.ui.component.TextField
import fptu.capstone.gymmanagesystem.ui.gymClass.getBitmapFromUri
import fptu.capstone.gymmanagesystem.ui.gymClass.saveBitmapToFile
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.utils.parseDateTime
import fptu.capstone.gymmanagesystem.viewmodel.UserViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ProfileDetailScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    userId: String
) {
    val context = LocalContext.current
    val userState by userViewModel.userState.collectAsState()
    val userUpdatedState by userViewModel.userUpdated.collectAsState()
    val genderOptions = listOf("Male", "Female", "Other")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("Choose gender") }
    val focusRequester = remember { FocusRequester() }
    val name by userViewModel.name.collectAsState()
    val phone by userViewModel.phone.collectAsState()
    val gender by userViewModel.gender.collectAsState()
    val dateOfBirth by userViewModel.dateOfBirth.collectAsState()
    val status by userViewModel.status.collectAsState()
    val avatar by userViewModel.avatar.collectAsState()
    val bitmap by userViewModel.imageBitmap.collectAsState()
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    var showSelectImageDialog by remember { mutableStateOf(false) }

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val galleryPermissionState = rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)

    val launcherTakePhoto =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            userViewModel.setImageBitmap(it)
            userViewModel.setCommunicationImage(saveBitmapToFile(it))
        }

    val launcherPickImage =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            userViewModel.setImageBitmap(it?.let { getBitmapFromUri(context, it) })
            userViewModel.setCommunicationImage(saveBitmapToFile(it?.let {
                getBitmapFromUri(
                    context,
                    it
                )
            }))
        }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
            val localDateTime = LocalDateTime.of(
                selectedYear,
                selectedMonth + 1,
                selectedDay,
                0,
                0, 0, 0
            )
            userViewModel.onDateOfBirthChange(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        }, year, month, day
    )

    LaunchedEffect(key1 = userId) {
        userViewModel.getUserById(userId)
    }

    LaunchedEffect(key1 = userUpdatedState) {
        if (userUpdatedState is DataState.Success) {
            Toast.makeText(context, "Update successfully", Toast.LENGTH_SHORT).show()
        } else if (userUpdatedState is DataState.Error) {
            Toast.makeText(
                context,
                "Update failed: ${(userUpdatedState as DataState.Error).message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    when (userState) {
        is DataState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(300.dp)
                    .fillMaxSize()
            )
        }

        is DataState.Error -> {
            Text(text = "Error: ${(userState as DataState.Error).message}")
        }

        is DataState.Success -> {
            val user = (userState as DataState.Success).data

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    if (bitmap == null)
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(user.avatarUrl)
                                .placeholder(R.drawable.avatar_placeholder)
                                .error(R.drawable.avatar_placeholder)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(75.dp)),
                            contentScale = ContentScale.Crop
                        )
                    else bitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "Image",
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(75.dp))
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(75.dp))
                            .background(color = Color.Gray.copy(alpha = 0.5f))
                            .clickable { showSelectImageDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_camera_alt_24),
                            contentDescription = "Update avatar"
                        )
                    }
                }
                Gap.k32.Height()
                TextField(
                    label = "Name",
                    value = name,
                    onTextChange = { userViewModel.onNameChange(it) })
                Gap.k16.Height()
                TextField(
                    label = "Phone",
                    value = phone,
                    keyboardOption = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                    onTextChange = { userViewModel.onPhoneChange(it) })
                Gap.k16.Height()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = Color.White)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            expanded = true
                        }, contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = gender.ifEmpty { "Gender" },
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    DropdownMenu(
                        modifier = Modifier
                            .padding(16.dp)
                            .clip(shape = RoundedCornerShape(16.dp)),
                        expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        genderOptions.forEach { option ->
                            Text(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        userViewModel.onGenderChange(option)
                                        expanded = false
                                    },
                                text = option
                            )
                        }

                    }
                }
                Gap.k16.Height()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = Color.White)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            datePickerDialog.show()
                        }, contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = if (dateOfBirth.isNotEmpty()) parseDateTime(dateOfBirth).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) else "Date of birth",
                        color = MaterialTheme.colorScheme.onSurface
                    )

                }
                Gap.k32.Height()
                LargeButton(
                    text = "Save",
                    isLoading = userUpdatedState is DataState.Loading,
                    onClick = {
                        userViewModel.updateProfile(
                            id = user.id!!,
                            name = name,
                            phone = phone,
                            gender = gender,
                            status = null,
                            dateOfBirth = dateOfBirth,
                            avatar = avatar
                        )
                    })
                Gap.k16.Height()
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Change password")
                }
            }
        }

        else -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(300.dp)
                    .fillMaxSize()
            )
        }
    }
    if (showSelectImageDialog) {
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
                                    cameraPermissionState.launchPermissionRequest()
                                    launcherTakePhoto.launch()
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
                                    galleryPermissionState.launchPermissionRequest()
                                    launcherPickImage.launch("image/*")
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