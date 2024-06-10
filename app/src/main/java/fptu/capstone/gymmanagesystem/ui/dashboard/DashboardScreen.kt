package fptu.capstone.gymmanagesystem.ui.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import fptu.capstone.gymmanagesystem.ui.login.LoginViewModel
import fptu.capstone.gymmanagesystem.ui.navigation.BottomBarNavigation
import fptu.capstone.gymmanagesystem.ui.profile.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController, loginViewModel: LoginViewModel) {
    val navControllerBottomBar = rememberNavController()
    Scaffold(bottomBar = { BottomNavigationBar(navController = navControllerBottomBar) }) {
        BottomBarNavigation(navController = navControllerBottomBar, modifier = Modifier.padding(it), loginViewModel = loginViewModel)
    }
}
