package fptu.capstone.gymmanagesystem.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fptu.capstone.gymmanagesystem.ui.dashboard.DashboardScreen
import fptu.capstone.gymmanagesystem.viewmodel.LoginViewModel

const val ROOT_ROUTE = "root"
@Composable
fun AppNavigation(modifier: Modifier = Modifier, loginViewModel: LoginViewModel, navHostController: NavHostController) {
    val navController = rememberNavController()
    NavHost(navController = navHostController, startDestination = Route.Dashboard.route, route = ROOT_ROUTE) {
        composable(Route.Dashboard.route) {
            DashboardScreen(navController = navHostController, loginViewModel = loginViewModel)
        }
//        composable(Route.Login.route) {
//            LoginScreen(loginViewModel = loginViewModel, onLoginSuccess = {navController.navigate(Route.Dashboard.route)})
//        }
    }
}