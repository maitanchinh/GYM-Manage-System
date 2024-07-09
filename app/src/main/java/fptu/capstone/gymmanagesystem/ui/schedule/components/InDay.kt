package fptu.capstone.gymmanagesystem.ui.schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.ui.component.Gap

@Composable
fun InDay(page: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Row(modifier = Modifier.padding(12.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://bizweb.dktcdn.net/100/011/344/files/tac-dung-cua-yoga.webp?v=1680778752047")
                        .placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
                        .build(),
                    contentDescription = "Course Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                )
                Gap.k16.Width()
                Column {
                    Text(
                        text = "#" + (page + 1).toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Gap.k8.Height()
                    Text(
                        text = "Gratitude meditations",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Gap.k8.Height()
                    Text(
                        text = "90 min",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            Gap.k4.Height()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Yoga for beginner",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium

                    )
                    Gap.k8.Height()
                    LinearProgressIndicator(
                        progress = { 0.3f }, modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(8.dp))
                    )
                    Gap.k8.Height()
                    BasicText(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Black,
                                    fontWeight = FontWeight.Medium
                                )
                            ) { append("10/30") }
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Gray,
                                )
                            ) { append(" lessons completed!") }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }

    }
}