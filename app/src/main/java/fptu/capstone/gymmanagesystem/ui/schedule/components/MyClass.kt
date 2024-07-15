package fptu.capstone.gymmanagesystem.ui.schedule.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.model.Course
import fptu.capstone.gymmanagesystem.model.Lesson
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.theme.Purple40
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun MyClass(modifier: Modifier = Modifier, course: Course, lessons: ArrayList<Lesson>) {
    val lessonsCompleted = lessons.filter { it.status == "Completed" }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(course.thumbnailUrl)
                    .placeholder(
                        R.drawable.placeholder
                    )
                    .error(
                        R.drawable.placeholder
                    )
                    .build(),
                contentDescription = "Class Thumbnail",
                modifier = Modifier
                    .size(50.dp)
                    .clip(shape = RoundedCornerShape(20)),
                contentScale = ContentScale.Crop
            )
            Gap.k16.Width()
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = course.classes[0].name!!,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                )
                Gap.k8.Height()
                Text(
                    text = "${LocalTime.parse(course.classes[0].from, DateTimeFormatter.ofPattern("HH:mm:ss")).format(DateTimeFormatter.ofPattern("HH:mm"))} - ${LocalTime.parse(course.classes[0].to, DateTimeFormatter.ofPattern("HH:mm:ss")).format(DateTimeFormatter.ofPattern("HH:mm"))}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            if (lessons.isNotEmpty())
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val size = size.minDimension
                        val stroke = Stroke(width = 15f)
                        val startAngle = -90f
                        val sweepAngle: Float = (360 * lessonsCompleted.size.toFloat() / lessons.size.toFloat() * 100 / 100).toFloat()

                        // Draw background circle
                        drawCircle(
                            color = Color.White,
                            radius = size / 2,
                            style = stroke
                        )

                        // Draw progress arc
                        drawArc(
                            color = Purple40,
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            style = stroke,
                            size = Size(size, size),
                            topLeft = Offset(0f, 0f)
                        )
                    }

                    // Draw percentage text
                    Text(
                        text = String.format("%.0f%%", (lessonsCompleted.size.toFloat() / lessons.size.toFloat() * 100)),
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
        }
    }
}