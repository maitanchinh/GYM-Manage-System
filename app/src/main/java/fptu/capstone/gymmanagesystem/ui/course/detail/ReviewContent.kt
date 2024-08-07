package fptu.capstone.gymmanagesystem.ui.course.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.ui.component.shimmerLoadingAnimation
import fptu.capstone.gymmanagesystem.ui.gymClass.component.FeedbackItem
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.viewmodel.FeedbackViewModel

@Composable
fun ReviewContent(
    modifier: Modifier = Modifier,
    courseId: String,
    feedbackViewModel: FeedbackViewModel = hiltViewModel(),
) {
    val feedbacksState by feedbackViewModel.feedbacks.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    LaunchedEffect(Unit) {
        feedbackViewModel.getFeedbacks(FilterRequestBody(courseId = courseId))
    }

    SwipeRefresh(
        modifier = modifier, state = swipeRefreshState, onRefresh = {
            isRefreshing = true
            feedbackViewModel.getFeedbacks(FilterRequestBody(courseId = courseId))
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

                if (feedbacks.isEmpty())
                    Text(text = "No feedback yet")
                else{
                    println("Feedbacks: $feedbacks")
                LazyColumn() {
                    items(feedbacks.size) { index ->
                        FeedbackItem(feedbacks[index])
                    }
                }
                }
            }

            is DataState.Error -> {
                val error = (feedbacksState as DataState.Error).message
                Text(text = error)
            }

            else -> {
            }
        }

    }
}