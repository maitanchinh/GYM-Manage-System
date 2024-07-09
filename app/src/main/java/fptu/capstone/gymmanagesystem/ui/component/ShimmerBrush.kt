package fptu.capstone.gymmanagesystem.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValues: Float = 1000f) : Brush {
    return if (showShimmer) {
        Brush.linearGradient(
            0f to Color.LightGray.copy(alpha = 0.5f),
            targetValues to Color.LightGray.copy(alpha = 0.1f)
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(Color.Transparent),
            startY = 0f,
            endY = 1000f
        )
    }
}