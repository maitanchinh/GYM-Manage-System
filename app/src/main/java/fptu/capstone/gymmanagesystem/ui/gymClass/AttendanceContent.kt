package fptu.capstone.gymmanagesystem.ui.gymClass

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.model.Attendance
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Lesson
import fptu.capstone.gymmanagesystem.ui.component.shimmerLoadingAnimation
import fptu.capstone.gymmanagesystem.ui.theme.ForestGreen
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.utils.parseDateTime
import fptu.capstone.gymmanagesystem.viewmodel.AttendanceViewModel
import fptu.capstone.gymmanagesystem.viewmodel.LessonViewModel
import java.time.LocalDateTime

@Composable
fun AttendanceContent(
    modifier: Modifier,
    classId: String,
    attendanceViewModel: AttendanceViewModel = hiltViewModel(),
    lessonViewModel: LessonViewModel = hiltViewModel()
) {
    val attendancesState by attendanceViewModel.attendances.collectAsState()
    val lessonsState by lessonViewModel.lessons.collectAsState()
    val isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    LaunchedEffect(Unit) {
        lessonViewModel.getLessons(
            FilterRequestBody(
                classId = classId,
                orderBy = "StartTime",
                isAscending = true
            )
        )
        attendanceViewModel.fetchAttendances(
            FilterRequestBody(
                classId = classId,
                orderBy = "CreateAt",
                isAscending = true
            )
        )
    }
    SwipeRefresh(
        modifier = modifier,
        state = swipeRefreshState, onRefresh = {
            lessonViewModel.getLessons(
                FilterRequestBody(
                    classId = classId,
                    orderBy = "StartTime",
                    isAscending = true
                )
            )
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
                when(attendancesState) {
                    is DataState.Success -> {
                        val attendances = (attendancesState as DataState.Success).data.data
                        val attendanceLessons = mapAttendanceToSlots(attendances, lessons)
                        val lastPastLessonIndex = attendanceLessons.indexOfLast { it.isPast }
                        val listState = rememberLazyListState()
                        LaunchedEffect(lastPastLessonIndex) {
                            if (lastPastLessonIndex != -1) {
                                listState.scrollToItem(lastPastLessonIndex - 1)
                            }
                        }
                        LazyColumn(modifier = modifier, state = listState, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            items(attendanceLessons.size) { index ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(shape = RoundedCornerShape(8.dp))
                                        .background(color = MaterialTheme.colorScheme.surface)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Text(text = lessons[index].name!!)
                                        if (lessons[index].isPast) {
                                            if (lessons[index].isAttendance) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.round_check_circle_outline_32),
                                                    contentDescription = null,
                                                    tint = ForestGreen
                                                )
                                            } else {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.round_do_not_disturb_32),
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.error
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is DataState.Error -> {
                       Text(text = (attendancesState as DataState.Error).message)}
                    else -> {}
                }


            }

            is DataState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = (lessonsState as DataState.Error).message)
                }
            }

            else -> {}
        }
    }
}

fun mapAttendanceToSlots(
    attendances: ArrayList<Attendance>,
    lessons: ArrayList<Lesson>
): ArrayList<Lesson> {
    val attendanceSet = attendances.map { it.slot!!.id }.toSet()
    val currentTime = LocalDateTime.now()
    lessons.forEach { lesson ->
        lesson.isAttendance = attendanceSet.contains(lesson.id)
        lesson.isPast = parseDateTime(lesson.endTime!!).isBefore(currentTime)
    }
    return lessons
}