@file:OptIn(ExperimentalMaterial3Api::class)

package fptu.capstone.gymmanagesystem.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.component.LargeButton
import fptu.capstone.gymmanagesystem.ui.component.TextField

@Composable
fun ProfileDetailScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),) {
        TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.background), title = { /*TODO*/ }, actions = {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = "Update avatar"
            )
        })
        Column(

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Image(
                    painter = rememberAsyncImagePainter("https://avatar.iran.liara.run/public/48"),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clipToBounds()
                )
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(75.dp))
                        .background(color = Color.Gray.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_camera_alt_24),
                        contentDescription = "Update avatar"
                    )
                }
            }
            Gap.k32.Height()
            TextField(label = "Name", value = "Nguyễn Văn An", onTextChange = {})
            Gap.k16.Height()
            TextField(label = "Email", value = "nguyenvana@gmail.com", onTextChange = {})
            Gap.k16.Height()
            TextField(label = "Phone", value = "0123456789", onTextChange = {})
            Gap.k16.Height()
            TextField(
                label = "Address",
                value = "1 Lê Duẫn, phường Bến Nghé, quận 1, HCM",
                onTextChange = {})
            Gap.k32.Height()
            LargeButton(text = "Save", isLoading = false, onClick = {})
            Gap.k16.Height()
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Change password")
            }
        }
    }
}