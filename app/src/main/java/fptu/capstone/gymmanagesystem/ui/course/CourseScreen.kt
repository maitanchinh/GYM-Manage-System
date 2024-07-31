@file:OptIn(ExperimentalMaterial3Api::class)

package fptu.capstone.gymmanagesystem.ui.course

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import fptu.capstone.gymmanagesystem.model.Course
import fptu.capstone.gymmanagesystem.model.CourseCategory
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.ui.component.IconTextField
import fptu.capstone.gymmanagesystem.ui.component.shimmerLoadingAnimation
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.viewmodel.CategoryViewModel
import fptu.capstone.gymmanagesystem.viewmodel.ClassViewModel
import fptu.capstone.gymmanagesystem.viewmodel.CourseViewModel
import fptu.capstone.gymmanagesystem.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseScreen(
    onViewAllClassClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onCourseClick: (id: String) -> Unit = {},
    onViewAllMyClassClick: () -> Unit = {},
    viewModel: CourseViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    classViewModel: ClassViewModel = hiltViewModel()
) {
    val categoryState by categoryViewModel.categories.collectAsState()
    val courses by viewModel.courses.collectAsState()
    val myCourses = viewModel.myCourses
    val classesState by classViewModel.classes.collectAsState()
    val wishlistsState by viewModel.wishlists.collectAsState()
    var searchText by remember { mutableStateOf("") }
    val notificationCount by remember { mutableStateOf(2) }
    var categories = arrayListOf("All")
    val selectedCategory = remember { mutableStateOf("All") }
    var isClassesRefreshing by remember { mutableStateOf(false) }
//    val swipeRefreshState by remember { mutableStateOf(SwipeRefreshState(isRefreshing = classes is DataState.Loading)) }
    if (categoryState is DataState.Success) {
        categories.addAll((categoryState as DataState.Success<Response<CourseCategory>>).data.data.map { it.name!! })
        if (selectedCategory.value == "All") {
            viewModel.fetchWishlists(FilterRequestBody(memberId = userViewModel.getUser()?.id))
            classViewModel.fetchClassesEnrolled(FilterRequestBody())
        } else {
            viewModel.fetchCourses(FilterRequestBody(categoryId = (categoryState as DataState.Success<Response<CourseCategory>>).data.data.find { it.name == selectedCategory.value }?.id))
            classViewModel.fetchClassesEnrolled(FilterRequestBody())
        }
    }
    var filteredItems by remember { mutableStateOf<List<Course>?>(null) }

    SwipeRefresh(
        state = SwipeRefreshState(isRefreshing = isClassesRefreshing),
        //        state = swipeRefreshState,
        onRefresh = {
            isClassesRefreshing = true
            viewModel.refreshCourses(FilterRequestBody())
            viewModel.fetchWishlists(FilterRequestBody(memberId = userViewModel.getUser()?.id))
            classViewModel.fetchClassesEnrolled(FilterRequestBody())
            isClassesRefreshing = false
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(span = { GridItemSpan(2) }) {
                    Column {
                        IconTextField(
                            value = searchText,
                            placeholder = "Search class",
                            onValueChange = { searchText = it })
                        //        Gap.k16.Height()

                        //        Gap.k16.Height()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Suggestion",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            TextButton(onClick = { onViewAllMyClassClick() }) {
                                Text(text = "View all")
                            }
                        }
                        FlowRow(mainAxisSpacing = 16.dp, crossAxisSpacing = 8.dp) {
                            categories.forEach {
                                val isSelected = selectedCategory.value == it
                                val backgroundColor =
                                    if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
                                Box(
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(50))
                                        .background(backgroundColor)
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .clickable { selectedCategory.value = it }
                                ) {
                                    Text(
                                        text = it,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
                when (courses) {
                    is DataState.Loading -> {
                        item(span = { GridItemSpan(1) }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(185.dp)
                                    .clip(shape = RoundedCornerShape(5))
                                    .shimmerLoadingAnimation()
                            )
                        }
                    }

                    is DataState.Error -> {
                        item {
                            Text(
                                text = "Error loading courses, please try refreshing",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    is DataState.Success -> {
                        val items =
                            (courses as? DataState.Success)?.data?.data ?: mutableListOf()
                        var coursesWithClass = mutableListOf<Course>()

                        if (wishlistsState is DataState.Success) {
                            for (i in items) {
                                classViewModel.fetchClassesEnrolled(
                                    FilterRequestBody(
                                        courseId = i.id
                                    )
                                )
                                if (classesState is DataState.Success) {
                                    val classes =
                                        (classesState as DataState.Success).data.data
                                    val classIds = classes.map { it.id }
                                    coursesWithClass = items.filter { item ->
                                        item.classes.any { it.id in classIds }
                                    }.toMutableList()


                                }
                            }
                            if (coursesWithClass.isNotEmpty()) {
                                for (course in coursesWithClass) {
                                    viewModel.addMyCourse(course)
                                    items.removeAll { item -> item.id == course.id }
                                }
                            }
                            val wishlists = (wishlistsState as DataState.Success).data.data
                            val courseWishlistIds = wishlists.map { it.course?.id }
                            items.removeAll { item -> item.id in courseWishlistIds }
                            filteredItems = items
                        }
                        filteredItems?.let { i ->
                            items(i.size) { index ->
                                CourseCard(
                                    course = items[index],
                                    onCourseClick = onCourseClick
                                )
                            }
                        } ?: run {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(185.dp)
                                        .clip(shape = RoundedCornerShape(5))
                                        .shimmerLoadingAnimation()
                                )
                            }
                        }
                    }

                    is DataState.Empty -> {
                        item {
                            Text(
                                text = "No classes found",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    else -> {
                    }
                }
                when (wishlistsState) {
                    is DataState.Error -> {
                        item {
                            Text(text = "Error loading wishlists, please try refreshing")
                        }
                    }

                    is DataState.Success -> {
                        val items =
                            (wishlistsState as? DataState.Success)?.data?.data ?: emptyList()
                        if (items.isNotEmpty()) {
                            item(span = { GridItemSpan(2) }) {
                                Text(
                                    text = "My Wishlist",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            items(items.size) { index ->
                                CourseCard(
                                    course = items[index].course!!,
                                    onCourseClick = onCourseClick
                                )
                            }

                        }
                    }

                    else -> {}
                }
                if (myCourses.isNotEmpty()) {
                    item(span = { GridItemSpan(2) }) {
                        Text(
                            text = "My Class",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    items(myCourses.size) { index ->
                        CourseCard(
                            course = myCourses[index],
                            onCourseClick = onCourseClick
                        )
                    }

                }
            }


        }
    }
}


