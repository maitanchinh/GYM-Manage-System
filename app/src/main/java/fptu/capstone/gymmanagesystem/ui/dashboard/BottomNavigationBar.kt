package fptu.capstone.gymmanagesystem.ui.dashboard

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import fptu.capstone.gymmanagesystem.ui.navigation.BottomNavItem
import fptu.capstone.gymmanagesystem.viewmodel.AuthViewModel

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val items = if (isLoggedIn) {
        listOf(
            BottomNavItem.Schedule,
            BottomNavItem.Course,
            BottomNavItem.Inquiry,
            BottomNavItem.Profile,
        )
    } else {
        listOf(
            BottomNavItem.Course,
            BottomNavItem.Profile,
        )
    }
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSurface
                ),
//                modifier = Modifier.background(color = MaterialTheme.colorScheme.primary),
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(item.title) },
                selected = if (currentRoute?.startsWith(item.route) == true) true else navBackStackEntry?.destination?.parent?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}