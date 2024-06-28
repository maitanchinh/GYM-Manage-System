@file:OptIn(ExperimentalMaterial3Api::class)

package fptu.capstone.gymmanagesystem.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(label: String? = null, value: String? = null, visualTransformation: VisualTransformation = VisualTransformation.None, maxLines: Int = 1, onTextChange: (String) -> Unit) {
    OutlinedTextField(
        value = value!!,
        onValueChange = onTextChange,
//        label = { Text(label!!) },
        placeholder = { Text(label!!) },
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(Color.White),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray,
        ),
        shape = RoundedCornerShape(16.dp),
        visualTransformation = visualTransformation,
        maxLines = maxLines,
        minLines = maxLines
    )
}