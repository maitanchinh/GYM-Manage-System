package fptu.capstone.gymmanagesystem.ui.gymclass.detail

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.ui.component.Gap

@Composable
fun ClassDetailScreen() {
    var selectedTab by remember { mutableStateOf("Overview") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
                Image(
                    painter = rememberAsyncImagePainter(model = "https://olimpsport.com/media/mageplaza/blog/post/image//w/y/wyprobuj-5-najlepszych-cwiczen-cardio-na-silowni_1.jpg"),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(5))
                        .aspectRatio(16f / 9f),
                    contentScale = ContentScale.FillBounds
                )
                Gap.k16.Height()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Cardio",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )

                    Text(text = "Ratings", color = Color.Gray, fontWeight = FontWeight.Medium)
                }
                Gap.k4.Height()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Cardio And Strength",
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
                        text = "members",
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
                        text = "36 lessons",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                    )
                }
            }
        }
        Gap.k32.Height()
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
                OverviewContent()
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
    }
}