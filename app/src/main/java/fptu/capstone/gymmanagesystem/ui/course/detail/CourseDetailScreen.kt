package fptu.capstone.gymmanagesystem.ui.course.detail

import android.widget.Toast
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
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
import fptu.capstone.gymmanagesystem.ui.component.LargeButton
import fptu.capstone.gymmanagesystem.ui.navigation.Route
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.viewmodel.ClassViewModel
import fptu.capstone.gymmanagesystem.viewmodel.CourseViewModel
import fptu.capstone.gymmanagesystem.viewmodel.FeedbackViewModel
import fptu.capstone.gymmanagesystem.viewmodel.UserViewModel

@Composable
fun CourseDetailScreen(
    courseId: String,
    onEnrollClick: (route: String) -> Unit,
    viewModel: CourseViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    classViewModel: ClassViewModel = hiltViewModel(),
    feedbackViewModel: FeedbackViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf("Overview") }
    val courseState = viewModel.course.collectAsState()
    val classesState by classViewModel.classes.collectAsState()
    val wishlistState by viewModel.wishlist.collectAsState()
    val wishlistsState by viewModel.wishlists.collectAsState()
    val feedbackState by feedbackViewModel.feedbacks.collectAsState()
    var isShowConfirmDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        viewModel.getCourseById(courseId)
        viewModel.fetchWishlists(
            FilterRequestBody(
                memberId = userViewModel.getUser()?.id,
                courseId = courseId
            )
        )
        classViewModel.fetchClassesEnrolled(FilterRequestBody(courseId = courseId))
        feedbackViewModel.getFeedbacks(FilterRequestBody(courseId = courseId))
    }

    LaunchedEffect(wishlistState) {
        if (wishlistState is DataState.Success) {
            val wishlist = (wishlistState as DataState.Success).data
            Toast.makeText(
                context,
                "You add course ${wishlist.course?.name} to wishlist",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.getCourseById(courseId)
            viewModel.fetchWishlists(
                FilterRequestBody(
                    memberId = userViewModel.getUser()?.id,
                    courseId = courseId
                )
            )
        } else if (wishlistState is DataState.Error) {
            val message = (wishlistState as DataState.Error).message
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    when (courseState.value) {
        is DataState.Success -> {
            val course = (courseState.value as DataState.Success).data
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
                        .clip(shape = RoundedCornerShape(5))
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
                                modifier = Modifier.fillMaxWidth(0.9f),
                                text = course.name ?: "No name",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (feedbackState is DataState.Success) {
                                        val feedbacks =
                                            (feedbackState as DataState.Success).data.data
                                        val score = feedbacks.map { it.score!! }.average()
                                        String.format("%.1f", score)
                                    } else {
                                        "0.0"
                                    },
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
                            Gap.k16.Width()
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
                when (wishlistsState) {
                    is DataState.Success -> {
                        val courses = (wishlistsState as DataState.Success).data.data
                        if (classesState is DataState.Success) {
                            val classes = (classesState as DataState.Success).data.data
                            if (classes.isNotEmpty()) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Gap.k16.Height()
                                    Text(
                                        text = "Your are already enrolled in this course",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Gray
                                    )
                                }
                            } else {
                                if (courses.isEmpty())
                                    Column {
                                        Gap.k16.Height()
                                        LargeButton(text = if (userViewModel.getUser()?.rank != "Basic") "Add to wishlist" else "Please upgrade your account", isLoading = false, isAlter = userViewModel.getUser()?.rank == "Basic") {
                                            if (userViewModel.getUser()?.rank != "Basic") {
                                                if (userViewModel.getUser() != null) {
                                                    isShowConfirmDialog = true
                                                } else {
                                                    onEnrollClick(Route.Profile.route)
                                                }
                                            }
                                        }
                                    }
                                else
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Gap.k16.Height()
                                        Text(
                                            text = "This course is in your wishlist",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.Gray
                                        )
                                    }
                            }
                        }

                    }

                    else -> {}
                }

                Gap.k16.Height()
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20))
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val tabs = listOf("Overview", "Instructor", "Reviews", "Requirements")

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
                when (selectedTab) {
                    "Overview" -> {
                        OverviewContent(course = course)
                    }

                    "Instructor" -> {
                        Text(text = "Instructor")
                    }

                    "Reviews" -> {
                        ReviewContent(
                            courseId = courseId,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                    }

                    "Requirements" -> {
                        Text(text = "Requirements")
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
    if (isShowConfirmDialog) {
        AlertDialog(
            onDismissRequest = { isShowConfirmDialog = false },
            title = {
                Text(text = "Enroll course")
            },
            text = {
                Text(text = "Are you sure you want to enroll this course?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.addWishlist(courseId)
                        isShowConfirmDialog = false
                    }
                ) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isShowConfirmDialog = false
                    }
                ) {
                    Text(text = "No")
                }
            }
        )
    }
}