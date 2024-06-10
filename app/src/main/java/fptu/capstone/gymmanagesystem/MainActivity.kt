package fptu.capstone.gymmanagesystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import fptu.capstone.gymmanagesystem.data.repository.UserRepository
import fptu.capstone.gymmanagesystem.ui.login.LoginViewModel
import fptu.capstone.gymmanagesystem.ui.login.LoginViewModelFactory
import fptu.capstone.gymmanagesystem.ui.navigation.AppNavigation
import fptu.capstone.gymmanagesystem.ui.profile.ProfileViewModel
import fptu.capstone.gymmanagesystem.ui.theme.GYMManageSystemTheme

class MainActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(UserRepository())
    }
    private val profileViewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val isDarkMode by profileViewModel.darkMode.observeAsState(false)
            GYMManageSystemTheme(darkTheme = isDarkMode) {
                var navHostController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        modifier = Modifier.fillMaxSize(),
                        loginViewModel = viewModel,
                        navHostController = navHostController,
                    )
                }
            }
        }
    }
}
