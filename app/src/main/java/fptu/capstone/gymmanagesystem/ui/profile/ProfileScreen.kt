package fptu.capstone.gymmanagesystem.ui.profile

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.component.LargeButton
import fptu.capstone.gymmanagesystem.ui.theme.GoldYellow
import fptu.capstone.gymmanagesystem.viewmodel.UserViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel(),
    onProfileDetailClick: (id: String) -> Unit,
    isLoading: Boolean,
    onLogoutClick: () -> Unit
) {
    val userState by userViewModel.userState.collectAsState()
    val isDarkMode by userViewModel.darkMode.observeAsState(initial = false)
    var user by remember { mutableStateOf(userViewModel.getUser()) }
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    LaunchedEffect(userState) {
        user = userViewModel.getUser()
    }
    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        isRefreshing = true
        userViewModel.getUserById(user?.id!!)
        isRefreshing = false
    }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(state = rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(user?.avatarUrl)
                            .placeholder(R.drawable.avatar_placeholder)
                            .error(R.drawable.avatar_placeholder)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(shape = RoundedCornerShape(75.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Column {
                        user?.name?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Text(
                            text = user?.email!!,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(24.dp)
                        )
                        .background(
                            color = if (user?.rank == "Basic") MaterialTheme.colorScheme.primaryContainer else GoldYellow.copy(
                                alpha = 0.2f
                            )
                        ),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                        text = user?.rank.toString(),
                        color = if (user?.rank == "Basic") MaterialTheme.colorScheme.primary else GoldYellow,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Gap.k32.Height()
            LargeButton(text = "Upgrade account", isLoading = false) {

            }
            Gap.k32.Height()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onProfileDetailClick(user?.id!!) },
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
                            onCheckedChange = { userViewModel.onDarkModeSwitchChanged(!isDarkMode) })
                    }
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            if (!isLoading) TextButton(onClick = onLogoutClick) {
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
}
