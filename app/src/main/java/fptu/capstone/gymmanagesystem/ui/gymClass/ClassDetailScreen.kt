package fptu.capstone.gymmanagesystem.ui.gymClass

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.course.detail.OverviewContent
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.viewmodel.ClassViewModel
import fptu.capstone.gymmanagesystem.viewmodel.CourseViewModel

@Composable
fun ClassDetailScreen(
    courseId: String,
    classId: String? = null,
    courseViewModel: CourseViewModel = hiltViewModel(),
    classViewModel: ClassViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableStateOf("Overview") }
    val courseState by courseViewModel.course.collectAsState()
    var tabs = emptyList<String>()
    LaunchedEffect(Unit) {
        courseViewModel.getCourseById(courseId)
        if (classId != null) {

            classViewModel.fetchClassesEnrolled(FilterRequestBody(courseId = courseId))
        }

    }
    when (courseState) {
        is DataState.Success -> {
            val course = (courseState as DataState.Success).data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 0.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
//                .graphicsLayer {
//                  shadowElevation = 2.dp.toPx()
//                    shape = RoundedCornerShape(5)
//                    clip = true
//                }
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(course.thumbnailUrl)
                                .placeholder(
                                    R.drawable.placeholder
                                ).error(R.drawable.placeholder).build(),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(5))
                                .aspectRatio(16f / 9f),
                            contentScale = ContentScale.Crop
                        )
                        Gap.k16.Height()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = course.category?.name ?: "No category",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            )

                            Text(
                                text = "Ratings",
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Gap.k4.Height()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = course.name ?: "No name",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "4.5",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Gap.k4.Width()
                                Icon(
                                    painter = painterResource(id = R.drawable.round_star_24),
                                    contentDescription = null,
                                    tint = Color(0xFFF2C300)
                                )
                            }

                        }
                        Gap.k4.Height()
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.round_groups_24),
                                contentDescription = null,
                                tint = Color.Gray
                            )
                            Gap.k8.Width()
                            Text(
                                text = "${course.totalMember} members",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                            )
                            Gap.k32.Width()
                            Icon(
                                painter = painterResource(id = R.drawable.round_access_time_24),
                                contentDescription = null,
                                tint = Color.Gray
                            )
                            Gap.k8.Width()
                            Text(
                                text = "${course.totalLesson} lessons",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                            )
                        }
                    }
                }
                Gap.k32.Height()
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (classId == null)
                            tabs = listOf("Overview", "Instructor", "Reviews", "Requirements")
                        else
                            tabs = listOf("Overview", "Communication", "Reviews", "Attendance")

                        tabs.forEach { tab ->
                            Box(
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(8.dp))
                                    .background(
                                        color = if (selectedTab == tab) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent
                                    )
                                    .clickable { selectedTab = tab }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = tab,
                                    color = if (selectedTab == tab) MaterialTheme.colorScheme.primary else Color.Gray,
                                    fontWeight = FontWeight.Medium,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }
                    }
                }
                Gap.k16.Height()
                if (classId == null)
                    when (selectedTab) {
                        "Overview" -> {
                            OverviewContent(course = course)
                        }

                        "Instructor" -> {
                            Text(text = "Instructor")
                        }

                        "Reviews" -> {
                            Text(text = "Reviews")
                        }

                        "Requirements" -> {
                            Text(text = "Requirements")
                        }
                    }
                else
                    when (selectedTab) {
                        "Overview" -> {
                            OverviewContent(course = course)
                        }

                        "Communication" -> {
                            CommunicationContent(modifier = Modifier.fillMaxSize().weight(1f), classId = classId)
                        }

                        "Reviews" -> {
                            Text(text = "Reviews")
                        }

                        "Attendance" -> {
                            AttendanceContent()
                        }
                    }
            }
        }

        is DataState.Error -> {
            Text(text = "Error")
        }

        is DataState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        }

        else -> {}
    }
}