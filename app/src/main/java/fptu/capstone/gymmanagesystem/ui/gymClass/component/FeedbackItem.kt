package fptu.capstone.gymmanagesystem.ui.gymClass.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.model.Feedback
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.theme.ForestGreen
import fptu.capstone.gymmanagesystem.ui.theme.GoldYellow

@Composable
fun FeedbackItem(
    feedback: Feedback,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(feedback.member?.avatarUrl)
                            .placeholder(
                                R.drawable.avatar_placeholder
                            ).error(R.drawable.avatar_placeholder).build(),
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(shape = RoundedCornerShape(20.dp))
                    )
                    Gap.k16.Width()
                    Text(
                        text = feedback.member?.name ?: "",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                BasicText(text = buildAnnotatedString {
                    withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                        append("Score: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            color = if (feedback.score!! in 70..100) ForestGreen else if (feedback.score!! in 40..69) GoldYellow else MaterialTheme.colorScheme.error
                        ),
                    ) {
                        append("${feedback.score}")
                    }
                }, style = MaterialTheme.typography.titleMedium)
            }
            Gap.k8.Height()
            Text(
                text = feedback.slot?.name ?: "",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = feedback.message ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}