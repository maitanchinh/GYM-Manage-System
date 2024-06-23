package fptu.capstone.gymmanagesystem.ui.navigation

import SessionManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fptu.capstone.gymmanagesystem.ui.gymclass.AllClassScreen
import fptu.capstone.gymmanagesystem.ui.gymclass.ClassScreen
import fptu.capstone.gymmanagesystem.ui.gymclass.detail.ClassDetailScreen
import fptu.capstone.gymmanagesystem.ui.home.HomeScreen
import fptu.capstone.gymmanagesystem.ui.login.LoginScreen
import fptu.capstone.gymmanagesystem.ui.profile.ProfileDetailScreen
import fptu.capstone.gymmanagesystem.ui.profile.ProfileScreen
import fptu.capstone.gymmanagesystem.ui.signup.SignupScreen
import fptu.capstone.gymmanagesystem.viewmodel.LoginViewModel

const val BOTTOM_BAR_ROUTE = "bottomBar"

@Composable
fun BottomBarNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel
) {
    val sessionManager = SessionManager.getInstance()
    var isLoggedIn by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        sessionManager.isLoggedIn().collect { value -> isLoggedIn = value }
    }
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier,
        route = BOTTOM_BAR_ROUTE
    ) {
        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.Profile.route) {
            if (isLoggedIn) {

                ProfileScreen(loginViewModel = loginViewModel, onProfileDetailClick = {
                    navController.navigate(Route.ProfileDetail.route)
                })
            } else {
                LoginScreen(
                    loginViewModel = loginViewModel,
                    onLoginSuccess = { navController.navigate(BottomNavItem.Profile.route) },
                    onSignUpClick = { navController.navigate(Route.Signup.route) })
            }
        }
        composable(BottomNavItem.Class.route) {
            ClassScreen(
                onViewAllMyClassClick = { navController.navigate(Route.AllClass.route) },
                onClassClick = { id -> navController.navigate(Route.ClassDetail.createRouteWithId(id)) })
        }
        composable(Route.Signup.route) {
            SignupScreen()
        }
        composable(Route.ProfileDetail.route) {
            ProfileDetailScreen()
        }
        composable(Route.AllClass.route) {
            AllClassScreen(onClassClick = { id -> navController.navigate(Route.ClassDetail.createRouteWithId(id)) })
        }
        composable(Route.ClassDetail.route) { backStackEntry ->
            val classId = backStackEntry.arguments?.getString("id")
            ClassDetailScreen(classId = classId!!)
        }
    }
}