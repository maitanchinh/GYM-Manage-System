package fptu.capstone.gymmanagesystem.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fptu.capstone.gymmanagesystem.ui.course.AllCourseScreen
import fptu.capstone.gymmanagesystem.ui.course.CourseScreen
import fptu.capstone.gymmanagesystem.ui.course.detail.CourseDetailScreen
import fptu.capstone.gymmanagesystem.ui.gymClass.ClassDetailScreen
import fptu.capstone.gymmanagesystem.ui.inquiry.InquiryDetailScreen
import fptu.capstone.gymmanagesystem.ui.inquiry.InquiryScreen
import fptu.capstone.gymmanagesystem.ui.login.LoginScreen
import fptu.capstone.gymmanagesystem.ui.profile.ProfileDetailScreen
import fptu.capstone.gymmanagesystem.ui.profile.ProfileScreen
import fptu.capstone.gymmanagesystem.ui.schedule.ScheduleScreen
import fptu.capstone.gymmanagesystem.ui.signup.SignupScreen
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.viewmodel.AuthViewModel

const val BOTTOM_BAR_ROUTE = "bottomBar"

@Composable
fun BottomBarNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val authState by authViewModel.authState.collectAsState()
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) BottomNavItem.Schedule.route else BottomNavItem.Course.route,
        modifier = modifier,
        route = BOTTOM_BAR_ROUTE
    ) {
        composable(BottomNavItem.Schedule.route) {
            ScheduleScreen(onClassClick = { courseId, classId ->
                navController.navigate(Route.ClassDetail.createRouteWithId(courseId, classId))
            })
        }
        composable(BottomNavItem.Profile.route) {
            if (isLoggedIn) {
                ProfileScreen(onProfileDetailClick = { id ->
                    navController.navigate(Route.ProfileDetail.createRouteWithId(id))
                },
                    isLoading = authState is DataState.Loading,
                    onLogoutClick = {
                        authViewModel.logout()
                    })
            } else {
                LoginScreen(authViewModel, navController = navController)
            }
        }
        composable(BottomNavItem.Course.route) {
            CourseScreen(
                onViewAllMyClassClick = { navController.navigate(Route.AllCourse.route) },
                onCourseClick = { id ->
                    navController.navigate(
                        Route.CourseDetail.createRouteWithId(
                            id
                        )
                    )
                })
        }
        composable(Route.Signup.route) {
            SignupScreen(navController = navController)
        }
        composable(Route.ProfileDetail.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("id")
            ProfileDetailScreen(userId = userId!!)
        }
        composable(Route.AllCourse.route) {
            AllCourseScreen(onCourseClick = { id ->
                navController.navigate(
                    Route.CourseDetail.createRouteWithId(
                        id
                    )
                )
            })
        }
        composable(Route.CourseDetail.route) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("id")
            CourseDetailScreen(courseId = courseId!!)
        }
        composable(Route.Inquiry.route) {
            InquiryScreen(navController = navController)
        }
        composable(Route.InquiryDetail.route) { backStackEntry ->
            val inquiryId = backStackEntry.arguments?.getString("id")
            InquiryDetailScreen(id = inquiryId!!)
        }
        composable(Route.ClassDetail.route) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId")
            val classId = backStackEntry.arguments?.getString("classId")
            ClassDetailScreen(courseId = courseId!!, classId = classId!!)
        }
    }
}