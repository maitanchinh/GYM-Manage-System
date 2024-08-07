package fptu.capstone.gymmanagesystem.ui.schedule

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.model.Course
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.GClass
import fptu.capstone.gymmanagesystem.model.Pagination
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.component.shimmerLoadingAnimation
import fptu.capstone.gymmanagesystem.ui.schedule.components.InDay
import fptu.capstone.gymmanagesystem.ui.schedule.components.MyClass
import fptu.capstone.gymmanagesystem.ui.schedule.components.WeekCalendar
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.viewmodel.ClassViewModel
import fptu.capstone.gymmanagesystem.viewmodel.CourseViewModel
import fptu.capstone.gymmanagesystem.viewmodel.UserViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel(),
    classViewModel: ClassViewModel = hiltViewModel(),
    courseViewModel: CourseViewModel = hiltViewModel(),
    onClassClick: (courseId: String, classId: String) -> Unit,
) {
    val notificationCount by remember { mutableStateOf(2) }
    val user = userViewModel.getUser()
    val classesState by classViewModel.classes.collectAsState()
    val coursesState by courseViewModel.courses.collectAsState()
    val lessons by classViewModel.lessons.collectAsState()
    val classesInDateState by classViewModel.classInDate.collectAsState()
    var courses: ArrayList<Course> = arrayListOf()
    var expendedClass by remember { mutableStateOf<Int?>(null) }
    when (coursesState) {
        is DataState.Success -> {
            courses = (coursesState as DataState.Success<Response<Course>>).data.data
        }

        else -> {}
    }

    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState by remember {
        mutableStateOf(SwipeRefreshState(isRefreshing = isRefreshing))
    }

    LaunchedEffect(Unit) {
        classViewModel.fetchClassesEnrolled(FilterRequestBody(status = "Active"))
        classViewModel.fetchLessons(
            FilterRequestBody(
                pagination = Pagination(pageSize = 100),
                orderBy = "StartTime",
                isAscending = true
            )
        )
    }

    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        isRefreshing = true
        classViewModel.fetchClassesEnrolled(FilterRequestBody(status = "Active"))
        classViewModel.fetchLessons(
            FilterRequestBody(
                pagination = Pagination(pageSize = 100),
                orderBy = "StartTime",
                isAscending = true
            )
        )
        isRefreshing = false
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(user?.avatarUrl)
                            .placeholder(R.drawable.avatar_placeholder)
                            .error(R.drawable.avatar_placeholder)
                            .build(),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(shape = RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Gap.k16.Width()
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Hello",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                        )
                        //                Gap.k8.Height()
                        Text(
                            text = user?.name!!,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Box(modifier = Modifier.size(40.dp)) {
                        IconButton(onClick = { /*TODO*/ }) {
                            BadgedBox(badge = {
                                if (notificationCount > 0) {
                                    Badge(
                                        containerColor = MaterialTheme.colorScheme.error,
                                        contentColor = Color.White,
                                        modifier = Modifier.offset(x = (-10).dp, y = 10.dp)
                                    ) {
                                        Text(text = notificationCount.toString())
                                    }
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.round_notifications_32),
                                    contentDescription = "Notification",
                                    tint = Color(0xFFED9455)
                                )
                            }
                        }
                    }
                }

            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        //                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(16.dp))
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                ) {
                    WeekCalendar()
                }

            }
            when (classesInDateState) {
                is DataState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(227.dp)
                                .clip(shape = RoundedCornerShape(16.dp))
                                .shimmerLoadingAnimation()
                        )
                    }
                }

                is DataState.Success -> {
                    val classes = (classesInDateState as DataState.Success<Response<GClass>>).data.data
                    if (classes.isEmpty()) {
                        item {
                            Text(text = "You have no class today")
                        }
                    }
                    else
                    items(classes.size) { index ->
                        val gClass = classes[index].copy(lessons = lessons.data.filter { it.classId == classes[index].id })
                        val myCourses = ArrayList<Course>()
                        courses.find { it.classes.find { cl -> cl.id == gClass.id } != null }
                            ?.copy(classes = arrayListOf(gClass))?.let { myCourses.add(it) }
                        if (myCourses.isNotEmpty()) {
                            HorizontalPager(
                                state = rememberPagerState(pageCount = { myCourses.size }),
                                pageSpacing = 16.dp
                            ) { page ->
                                InDay(myCourses[page])
                            }
                        } else Text(text = "You have no class")
                    }

                }

                is DataState.Error -> {
                    item { Text(text = "Something went wrong, please try again later") }
                }

                else -> {
                    // Empty
                }
            }
            item {
                Column {
                    Gap.k16.Height()
                    Text(
                        text = "Overview",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Gap.k16.Height()
                    when (classesState) {
                        is DataState.Loading -> {
                            Row {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(97.dp)
                                        .clip(shape = RoundedCornerShape(20))
                                        .shimmerLoadingAnimation()
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(97.dp)
                                        .clip(shape = RoundedCornerShape(20))
                                        .shimmerLoadingAnimation()
                                )
                            }
                        }

                        is DataState.Success -> {
                            val classes = (classesState as DataState.Success<Response<GClass>>).data.data
                            val duration = classes.sumOf { it.lessonCount!! }
                            Row {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(shape = RoundedCornerShape(16.dp))
                                        .background(MaterialTheme.colorScheme.secondaryContainer)
                                        //                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(16.dp))
                                        .padding(16.dp)
                                ) {
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.round_class_24),
                                                contentDescription = null,
                                                tint = Color.Gray
                                            )
                                            Gap.k8.Width()
                                            Text(
                                                text = "Education",
                                                style = MaterialTheme.typography.titleLarge,
                                                //                            fontWeight = FontWeight.Medium
                                                color = Color.Gray
                                            )
                                        }
                                        Gap.k8.Height()
                                        BasicText(text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = Color.Black,
                                                    fontSize = 24.sp,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            ) { append(classes.size.toString()) }
                                            withStyle(
                                                style = SpanStyle(
                                                    color = Color.Gray,
                                                )
                                            ) { append(" classes") }
                                        }, style = MaterialTheme.typography.bodyMedium)
                                    }
                                }
                                Gap.k16.Width()
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(shape = RoundedCornerShape(16.dp))
                                        .background(MaterialTheme.colorScheme.secondaryContainer)
                                        //                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(16.dp))
                                        .padding(16.dp)
                                ) {
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.round_access_time_filled_24),
                                                contentDescription = null,
                                                tint = Color.Gray
                                            )
                                            Gap.k8.Width()
                                            Text(
                                                text = "Duration",
                                                style = MaterialTheme.typography.titleLarge,
                                                //                            fontWeight = FontWeight.Medium,
                                                color = Color.Gray
                                            )
                                        }
                                        Gap.k8.Height()
                                        BasicText(text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = Color.Black,
                                                    fontSize = 24.sp,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            ) { append(duration.toString()) }
                                            withStyle(
                                                style = SpanStyle(
                                                    color = Color.Gray,
                                                )
                                            ) { append(" lessons") }

                                        }, style = MaterialTheme.typography.bodyMedium)
                                    }
                                }
                            }
                        }

                        is DataState.Error -> {
                            Text(text = "Something went wrong, please try again later")
                        }

                            else -> {
                                // Empty
                            }
                    }
                }

            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
//                        .padding(horizontal = 16.dp)
                ) {
                    Gap.k16.Height()
                    Text(
                        text = "My Classes",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                }
            }
            when (classesState) {
                is DataState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .clip(shape = RoundedCornerShape(20))
                                .shimmerLoadingAnimation()
                        )
                    }
                }

                is DataState.Success -> {
                    val classes = classesState as DataState.Success<Response<GClass>>
                    if (classes.data.data.isEmpty()) {
                        item {
                            Text(text = "You have no class")
                        }
                    }
                    items(classes.data.data.size) { index ->
                        val gClass = classes.data.data[index]
                        .copy(lessons = lessons.data.filter { it.classId == classes.data.data[index].id })
                        val myCourse =
                            courses.find { it.classes.find { cl -> cl.id == gClass.id } != null }
                                ?.copy(classes = arrayListOf(gClass))
                        if (myCourse != null) {
                            MyClass(course = myCourse) {
                                onClassClick(myCourse.id!!, gClass.id!!)
                            }
                        } else Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .clip(shape = RoundedCornerShape(20))
                                .shimmerLoadingAnimation()
                        )
                    }

                }

                is DataState.Error -> {
                    item { Text(text = "Something went wrong, please try again later") }
                }

                else -> {
                    // Empty
                }
            }

        }
    }
}



