package fptu.capstone.gymmanagesystem.ui.gymClass

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.component.TextField
import fptu.capstone.gymmanagesystem.ui.component.shimmerLoadingAnimation
import fptu.capstone.gymmanagesystem.ui.gymClass.component.Communication
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.viewmodel.CommunicationViewModel

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

    LaunchedEffect(Unit) {
        communicationViewModel.fetchCommunications(
            FilterRequestBody(
                orderBy = "CreateAt",
                isAscending = true
            )
        )
    }

    LaunchedEffect(key1 = sendCommunicationState) {
        if (sendCommunicationState is DataState.Success) {
            communicationViewModel.fetchCommunications(
                FilterRequestBody(
                    orderBy = "CreateAt",
                    isAscending = true
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
                    isAscending = true
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
            LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(communications) { communication ->
                    Communication(communication, onReply = { id ->
                        communicationViewModel.setIsReplying()
                        communicationViewModel.setSelectedCommunication(communication)
                    })
                }
            }
            Gap.k16.Height()
            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp)
            ) {
                TextField(
                    label = "Write a something...",
                    value = if (isCommenting) comment else communicationContent,
                    onTextChange = {
                        if (isCommenting) communicationViewModel.setComment(it) else communicationViewModel.setCommunicationContent(it)
                    },
                    prefix = {
                        if (!isCommenting) IconButton(onClick = {
                            communicationViewModel.setIsReplying()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.round_photo_camera_24),
                                contentDescription = "Image",
                            )
                        }
                    },
                    suffix = {
                        when (sendCommentState) {
                            is DataState.Loading -> {
                                CircularProgressIndicator()
                            }

                            else -> {
                                IconButton(onClick = {
                                    if (isCommenting)
                                        communicationViewModel.sendComment(
                                            communicationId = selectedCommunication?.id!!
                                        )
                                    else
                                        communicationViewModel.postCommunication(
                                            classId = classId,
                                        )
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.round_send_24),
                                        contentDescription = "Send Comment",
                                        tint = if (communicationContent.isNotEmpty() || comment.isNotEmpty()) MaterialTheme.colorScheme.primary else Color.Gray
                                    )
                                }
                            }
                        }
                    }
                )

            }
        }

        is DataState.Error -> {
            Text(text = (communicationState as DataState.Error).message)
        }

        else -> {}
    }
}

