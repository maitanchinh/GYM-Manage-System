package fptu.capstone.gymmanagesystem.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.viewmodel.LoginViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    profileViewModel: ProfileViewModel = viewModel(),
    onProfileDetailClick: () -> Unit
) {
    val isLoading by loginViewModel.isLoading
    val isDarkMode by profileViewModel.darkMode.observeAsState(initial = false)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://avatar.iran.liara.run/public/48"),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clipToBounds()
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "Nguyễn Văn An",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = "nguyenvanan@gmail.com",
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(24.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { onProfileDetailClick() },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,

                ) {
                    Icon(imageVector = Icons.Rounded.Person, contentDescription = null)
                    Spacer(modifier = Modifier.padding(start = 32.dp))
                    Text(
                        "Profile Details"
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_payment_2),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(start = 32.dp))
                    Text(
                        "Billing",
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_dark_mode_24),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(start = 32.dp))
                    Text(
                        "Dark mode",
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        modifier = Modifier.height(24.dp),
                        checked = isDarkMode,
                        onCheckedChange = { profileViewModel.onDarkModeSwitchChanged(!isDarkMode) })
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        if (!isLoading) TextButton(onClick = { loginViewModel.logout() }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = null)
                Spacer(modifier = Modifier.padding(4.dp))
                Text("Log out")
            }
        } else CircularProgressIndicator()
    }
}
