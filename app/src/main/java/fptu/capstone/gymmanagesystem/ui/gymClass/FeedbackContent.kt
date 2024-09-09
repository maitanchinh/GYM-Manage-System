package fptu.capstone.gymmanagesystem.ui.gymClass

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.network.FeedbackRequestBody
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.component.TextField
import fptu.capstone.gymmanagesystem.ui.component.shimmerLoadingAnimation
import fptu.capstone.gymmanagesystem.ui.gymClass.component.FeedbackItem
import fptu.capstone.gymmanagesystem.ui.theme.ForestGreen
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.utils.parseDateTime
import fptu.capstone.gymmanagesystem.viewmodel.FeedbackViewModel
import fptu.capstone.gymmanagesystem.viewmodel.LessonViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun FeedbackContent(
    modifier: Modifier = Modifier,
    feedbackViewModel: FeedbackViewModel = hiltViewModel(),
    lessonViewModel: LessonViewModel = hiltViewModel(),
    classId: String
) {
    val context = LocalContext.current
    val tabs = listOf("Feedbacks", "Lessons")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val feedbacksState by feedbackViewModel.feedbacks.collectAsState()
    val lessonsState by lessonViewModel.lessons.collectAsState()
    var isRefreshing by remember {
        mutableStateOf(false)
    }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    val isShowDialog by feedbackViewModel.isShowFeedbackDialog.collectAsState()
    val feedbackMessage by feedbackViewModel.feedbackMessage.collectAsState()
    val feedbackScore by feedbackViewModel.feedbackScore.collectAsState()
    val feedbackSlotId by feedbackViewModel.feedbackSlotId.collectAsState()
    val feedback by feedbackViewModel.feedback.collectAsState()

    LaunchedEffect(Unit) {
        feedbackViewModel.getFeedbacks(
            filterRequestBody = FilterRequestBody(
                classId = classId
            )
        )
        lessonViewModel.getLessons(filterRequestBody = FilterRequestBody(classId = classId))
    }

    LaunchedEffect(feedback) {
        if (feedback is DataState.Success) {
            feedbackViewModel.getFeedbacks(
                filterRequestBody = FilterRequestBody(
                    classId = classId
                )
            )
            Toast.makeText(context, "Feedback successfully", Toast.LENGTH_SHORT).show()
        } else if (feedback is DataState.Error) {
            Toast.makeText(context, (feedback as DataState.Error).message, Toast.LENGTH_SHORT)
                .show()
        }
    }

//    LaunchedEffect(openCloseFeedbackState) {
//        if (openCloseFeedbackState is DataState.Success) {
//            lessonViewModel.getLessons(filterRequestBody = FilterRequestBody(classId = classId))
//            Toast.makeText(context, "Open/Close feedback successfully", Toast.LENGTH_SHORT).show()
//        } else if (openCloseFeedbackState is DataState.Error) {
//            Toast.makeText(context, "Open/Close feedback failed", Toast.LENGTH_SHORT).show()
//        }
//    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEach { tab ->
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(
                        color = if (selectedTabIndex == tabs.indexOf(tab)) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent
                    )
                    .clickable { selectedTabIndex = tabs.indexOf(tab) }
                    .padding(8.dp)
            ) {
                Text(
                    text = tab,
                    color = if (selectedTabIndex == tabs.indexOf(tab)) MaterialTheme.colorScheme.primary else Color.Gray,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
    when (selectedTabIndex) {
        0 -> SwipeRefresh(
            modifier = modifier.padding(vertical = 16.dp),
            state = swipeRefreshState, onRefresh = {
                isRefreshing = true
                feedbackViewModel.getFeedbacks(
                    filterRequestBody = FilterRequestBody(
                        classId = classId
                    )
                )
                isRefreshing = false
            }) {
            when (feedbacksState) {
                is DataState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(shape = RoundedCornerShape(8.dp))
                            .shimmerLoadingAnimation()
                    )
                }

                is DataState.Success -> {
                    val feedbacks = (feedbacksState as DataState.Success).data.data
                    LazyColumn() {
                        items(feedbacks.size) { index ->
                            FeedbackItem(feedbacks[index])
                        }
                    }
                }

                is DataState.Error -> {
                    Text(text = "Something went wrong, please try again later")
                }

                else -> {}
            }
        }

        1 -> SwipeRefresh(
            modifier = modifier.padding(vertical = 16.dp),
            state = swipeRefreshState, onRefresh = {
                isRefreshing = true
                lessonViewModel.getLessons(filterRequestBody = FilterRequestBody(classId = classId))
                isRefreshing = false
            }) {
            when (lessonsState) {
                is DataState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(shape = RoundedCornerShape(8.dp))
                            .shimmerLoadingAnimation()
                    )
                }

                is DataState.Success -> {
                    val lessons = (lessonsState as DataState.Success).data.data
                    println(lessons)
                    val listState = rememberLazyListState()
                    val currentLesson = lessons.firstOrNull() {
                        parseDateTime(it.endTime!!).isAfter(
                            LocalDateTime.now()
                        )
                    }
                    LaunchedEffect(Unit) {
                        if (currentLesson != null)
                            listState.scrollToItem(lessons.indexOf(currentLesson) - 1)
                    }
                    when (feedbacksState) {
                        is DataState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .clip(shape = RoundedCornerShape(8.dp))
                                    .shimmerLoadingAnimation()
                            )
                        }

                        is DataState.Success -> {
                            val feedbacks = (feedbacksState as DataState.Success).data.data
                            lessons.forEach { lesson ->
                                feedbacks.forEach { feedback ->
                                    if (lesson.id == feedback.slot?.id) {
                                        lesson.feedback = feedback
                                    }
                                }
                            }
                        }

                        is DataState.Error -> {
                            Text(text = "Something went wrong, please try again later")
                        }

                        else -> {}
                    }
                    LazyColumn(
                        modifier = modifier,
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(lessons.size) { index ->
                            val startTime =
                                parseDateTime(lessons[index].startTime!!).format(
                                    DateTimeFormatter.ofPattern("HH:mm")
                                )
                            val endTime =
                                parseDateTime(lessons[index].endTime!!).format(
                                    DateTimeFormatter.ofPattern("HH:mm")
                                )
                            val date =
                                parseDateTime(lessons[index].startTime!!).format(
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(8.dp))
                                    .background(color = if (currentLesson != null && currentLesson.id == lessons[index].id) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.secondaryContainer)
                                    .clickable {
                                        if (currentLesson != null && currentLesson.id == lessons[index].id && lessons[index].feedbackStatus!!) {
                                            feedbackViewModel.setShowFeedbackDialog()
                                            feedbackViewModel.setFeedbackSlotId(lessons[index].id!!)
                                        }
                                    }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        if (lessons[index].feedbackStatus!!)
                                            Icon(
                                                painter = painterResource(id = R.drawable.round_lock_open_24),
                                                contentDescription = "Open feedback",
                                                tint = ForestGreen
                                            )
                                        else Icon(
                                            painter = painterResource(id = R.drawable.round_lock_outline_24),
                                            contentDescription = "Close feedback"
                                        )
                                        Gap.k8.Width()
                                        Column {
                                            Text(
                                                text = lessons[index].name!!,
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                            Text(text = "$startTime - $endTime  $date")
                                        }
                                    }
                                    if (lessons[index].feedback != null)
                                        Icon(
                                            painter = painterResource(id = R.drawable.round_check_circle_outline_32),
                                            contentDescription = null,
                                            tint = ForestGreen
                                        )
                                }
                            }
                        }
                    }
                }

                is DataState.Error -> {
                    Text(text = "Something went wrong, please try again later")
                }

                else -> {}
            }
        }
    }

    if (isShowDialog) {
        AlertDialog(
            onDismissRequest = {
                feedbackViewModel.setShowFeedbackDialog()
                feedbackViewModel.setFeedbackScore(0)
            },
            confirmButton = {
                TextButton(onClick = {
                    if (feedbackScore in 1..5) {
                        feedbackViewModel.setShowFeedbackDialog()
                        feedbackViewModel.sendFeedback(
                            feedback = FeedbackRequestBody(
                                message = feedbackMessage,
                                score = feedbackScore
                            ),
                            slotId = feedbackSlotId
                        )
                    }
                }) {
                    Text(
                        text = "Send",
                        color = if (feedbackScore in 1..5) MaterialTheme.colorScheme.onPrimaryContainer else Color.Gray
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    feedbackViewModel.setShowFeedbackDialog()
                    feedbackViewModel.setFeedbackScore(0)
                }) {
                    Text(text = "Cancel", color = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            },
            text = {
                Column {
                    TextField(
                        label = "Message",
                        maxLines = 7,
                        value = feedbackMessage,
                        onTextChange = {
                            feedbackViewModel.setFeedbackMessage(it)
                        }
                    )
                    Gap.k16.Height()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (i in 1..5) {
                            Icon(
                                modifier = Modifier.clickable {
                                    feedbackViewModel.setFeedbackScore(i)
                                },
                                painter = painterResource(id = R.drawable.round_star_rate_32),
                                contentDescription = null,
                                tint = if (i <= feedbackScore) MaterialTheme.colorScheme.onSurface else Color.Gray
                            )
                        }
                    }
                }
            })
    }
}