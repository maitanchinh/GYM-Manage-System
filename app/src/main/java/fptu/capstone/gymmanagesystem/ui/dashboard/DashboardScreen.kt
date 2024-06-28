package fptu.capstone.gymmanagesystem.ui.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import fptu.capstone.gymmanagesystem.ui.navigation.BottomBarNavigation

@Composable
fun DashboardScreen(navController: NavHostController) {
    val navControllerBottomBar = rememberNavController()
    Scaffold(bottomBar = { BottomNavigationBar(navController = navControllerBottomBar) }) {
        BottomBarNavigation(navController = navControllerBottomBar, modifier = Modifier.padding(it))
    }
}
