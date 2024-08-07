package fptu.capstone.gymmanagesystem.ui.course

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.model.Course
import fptu.capstone.gymmanagesystem.ui.component.Gap

@Composable
fun CourseCard(course: Course = Course(), onCourseClick: (id: String) -> Unit) {
    val thumbnailUrl = course.thumbnailUrl
    Box(
        modifier = Modifier
//                        .padding(8.dp)
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(5))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable { onCourseClick(course.id!!) },
        contentAlignment = Alignment.Center
    ) {

        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(thumbnailUrl)
                    .placeholder(
                        R.drawable.placeholder
                    ).error(R.drawable.placeholder).build(),
                contentDescription = "Class Thumbnail",
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop
            )
//            Image(
//                painter = rememberAsyncImagePainter(model = gymClass.thumbnailUrl ?: ""),
//                contentDescription = "Class Thumbnail",
//                modifier = Modifier
//                    .fillMaxSize()
//                    .aspectRatio(16f / 9f),
//                contentScale = ContentScale.Crop
//            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = course.name ?: "No name",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
//                Gap.k4.Height()
//                Text(
//                    text = "by ${course.trainer?.name ?: "No trainer"}",
//                    style = MaterialTheme.typography.bodySmall,
//                )
//                Gap.k4.Height()
//                Text(
//                    text = "1,050,000 ₫",
//                    style = MaterialTheme.typography.bodyLarge,
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.primary
//                )
                Gap.k4.Height()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${course.totalMember ?: 0} slots",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = "${course.totalLesson} lessons",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}