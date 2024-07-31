package fptu.capstone.gymmanagesystem.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LargeButton(modifier: Modifier = Modifier, text: String, isLoading: Boolean, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(8.dp).size(20.dp), color = Color.White)
        } else {
            Text(text, modifier = Modifier.padding(8.dp), style = MaterialTheme.typography.titleMedium)
        }
    }
}