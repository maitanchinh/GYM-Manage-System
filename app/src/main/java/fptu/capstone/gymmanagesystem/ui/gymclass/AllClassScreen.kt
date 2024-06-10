package fptu.capstone.gymmanagesystem.ui.gymclass

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.flowlayout.FlowRow
import fptu.capstone.gymmanagesystem.ui.component.Gap

@Composable
fun AllClassScreen() {
    val category = listOf("All", "Cardio", "Strength", "Yoga", "Dance", "Boxing", "Pilates")
    val selectedCategory = remember { mutableStateOf("All") }
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        item(span = { GridItemSpan(2) }) {
            Column {
                FlowRow(mainAxisSpacing = 16.dp, crossAxisSpacing = 8.dp) {
                    category.forEach {
                        val isSelected = it == selectedCategory.value
                        val backgroundColor =
                            if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(50))
                                .background(backgroundColor)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clickable { selectedCategory.value = it }
                        ) {
                            Text(text = it)
                        }
                    }
                }
            }
        }
        items(10) {
            Box(
                modifier = Modifier
//                        .padding(8.dp)
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(10))
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Image(
                        painter = rememberAsyncImagePainter(model = "https://olimpsport.com/media/mageplaza/blog/post/image//w/y/wyprobuj-5-najlepszych-cwiczen-cardio-na-silowni_1.jpg"),
                        contentDescription = "Class Thumbnail",
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(16f / 9f),
                        contentScale = ContentScale.FillBounds
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Cardio And Strength",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                        )
                        Gap.k4.Height()
                        Text(
                            text = "by Antonio",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        Gap.k4.Height()
                        Text(
                            text = "1,050,000 ₫",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Gap.k4.Height()
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = "49/50 slots",
                                style = MaterialTheme.typography.bodySmall,
                            )
                            Text(
                                text = "36 lessons",
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                }
            }
        }
    }
}