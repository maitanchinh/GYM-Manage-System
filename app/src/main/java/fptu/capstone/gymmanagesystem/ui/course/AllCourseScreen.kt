package fptu.capstone.gymmanagesystem.ui.course

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import com.google.accompanist.flowlayout.FlowRow
import fptu.capstone.gymmanagesystem.model.CourseCategory
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.component.IconTextField
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.viewmodel.CategoryViewModel
import fptu.capstone.gymmanagesystem.viewmodel.CourseViewModel

@Composable
fun AllCourseScreen(viewModel: CourseViewModel = hiltViewModel(), categoryViewModel: CategoryViewModel = hiltViewModel(), onCourseClick: (id: String) -> Unit = {}) {
    val classes = viewModel.courses.collectAsState()
    val category = listOf("All", "Cardio", "Strength", "Yoga", "Dance", "Boxing", "Pilates")
    val selectedCategory = remember { mutableStateOf("All") }
    val categoryState by categoryViewModel.categories.collectAsState()
    var categories = arrayListOf("All")
    var searchText by remember { mutableStateOf("") }
    if (categoryState is DataState.Success) {
        categories.addAll((categoryState as DataState.Success<Response<CourseCategory>>).data.data.map { it.name!! })
        if (selectedCategory.value == "All") {
            viewModel.fetchCourses(FilterRequestBody())
        } else {
            viewModel.fetchCourses(FilterRequestBody(categoryId = (categoryState as DataState.Success<Response<CourseCategory>>).data.data.find { it.name == selectedCategory.value }?.id))
        }
    }

    LaunchedEffect(searchText) {
        viewModel.fetchCourses(FilterRequestBody(search = searchText))
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val items = (classes.value as? DataState.Success)?.data?.data ?: emptyList()
//        val items = classes.value.classes
        item(span = { GridItemSpan(2) }) {
            Column {
                IconTextField(
                    value = searchText,
                    placeholder = "Search course",
                    onValueChange = { searchText = it })
                Gap.k16.Height()
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
                            Text(text = it, color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }
        items(items.size) { index ->
            CourseCard(items[index], onCourseClick = onCourseClick)
        }
    }
}