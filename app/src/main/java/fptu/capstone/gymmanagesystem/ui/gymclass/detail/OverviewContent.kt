package fptu.capstone.gymmanagesystem.ui.gymclass.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.ui.component.Gap

@Composable
fun OverviewContent() {
    var isExpanded by remember { mutableStateOf(false) }
    val fullText = "Cardio, short for cardiovascular exercise, refers to intentional physical activity aimed at improving either health or performance. Unlike weight lifting or yoga, which primarily target skeletal muscles, cardio focuses on taxing the heart and lungs. It involves exercising at a constant level of easy intensity for a specified duration (usually a minimum of 30 minutes). Easy intensity allows the cardiovascular system to replenish oxygen to working muscles efficiently.\n" +
            "\n" +
            "Cardiovascular training enhances the heart’s ability to pump blood, improves lung function, and burns calories. Whether it’s running, swimming, or dancing, cardio keeps your heart healthy and contributes to overall well-being. Remember, a healthy heart is essential for optimal bodily function, as it ensures oxygen and nutrients reach all cells and tissues. Let’s prioritize cardio and keep our hearts strong!"
    val previewText = if (fullText.length > 200) fullText.substring(0, 200) + "..." else fullText

    Column {
        Text("Class Information", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        Gap.k16.Height()
        val text = if (isExpanded) fullText else previewText
        val annotatedText = buildAnnotatedString {
            append(text)
            append(" ")
            pushStringAnnotation(tag = "showMoreOrLess", annotation = "")
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
                append(if (isExpanded) "Show Less" else "Show More")
            }
            pop()
        }
        ClickableText(
            text = annotatedText,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
            onClick = { offset ->
                annotatedText.getStringAnnotations(tag = "showMoreOrLess", start = offset, end = offset)
                    .firstOrNull()?.let {
                        isExpanded = !isExpanded
                    }
            },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Gap.k16.Height()
        Text("Overview", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        Gap.k16.Height()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.round_access_time_24), contentDescription = null, tint = Color.Gray)
            Gap.k8.Width()
            Text(text = "90 minutes per lesson", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
        Gap.k8.Height()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.round_check_circle_24), contentDescription = null, tint = Color.Gray)
            Gap.k8.Width()
            Text(text = "1234 students completed", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
        Gap.k8.Height()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.round_emoji_people_24), contentDescription = null, tint = Color.Gray)
            Gap.k8.Width()
            Text(text = "The instructor has a teaching certificate", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
        Gap.k8.Height()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.round_fitness_center_24), contentDescription = null, tint = Color.Gray)
            Gap.k8.Width()
            Text(text = "Well-equipped", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
        Gap.k8.Height()
    }
}