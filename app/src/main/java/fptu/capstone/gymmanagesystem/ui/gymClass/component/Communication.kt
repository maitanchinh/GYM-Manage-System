package fptu.capstone.gymmanagesystem.ui.gymClass.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.model.Communication
import fptu.capstone.gymmanagesystem.ui.component.Gap

@Composable
fun Communication(communication: Communication, onReply: (communication: Communication) -> Unit) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(targetValue = offsetX, label = "")
    Column(modifier = Modifier.pointerInput(Unit) {
        detectHorizontalDragGestures(onDragEnd = {
            offsetX = if (offsetX < -200f) {
                -200f
            } else {
                0f
            }
        }) { change, dragAmount ->
            val newOffsetX = offsetX + dragAmount
            if (newOffsetX in -300f..0f) {
                offsetX = newOffsetX
                change.consume()
            }
        }
    }) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = { onReply(communication) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_mode_comment_24),
                        contentDescription = "Reply",
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .offset { IntOffset(animatedOffsetX.toInt(), 0) }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(communication.user?.avatarUrl)
                        .placeholder(
                            R.drawable.avatar_placeholder
                        ).error(R.drawable.avatar_placeholder).build(),
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(shape = RoundedCornerShape(25.dp))
                )
                Gap.k16.Width()
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = communication.user?.name ?: "", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = communication.message!!)

                    }
                }
            }
        }
        if (communication.classCommunicationComments.isNotEmpty()) {
            communication.classCommunicationComments.forEach {
                Comment(modifier = Modifier.padding(start = 32.dp, top = 8.dp), comment = it)
            }
        }
    }
}