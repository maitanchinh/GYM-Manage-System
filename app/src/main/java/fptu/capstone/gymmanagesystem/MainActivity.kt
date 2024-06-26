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
import dagger.hilt.android.AndroidEntryPoint
import fptu.capstone.gymmanagesystem.ui.navigation.AppNavigation
import fptu.capstone.gymmanagesystem.ui.theme.GYMManageSystemTheme
import fptu.capstone.gymmanagesystem.viewmodel.UserViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val isDarkMode by userViewModel.darkMode.observeAsState(false)
            GYMManageSystemTheme(darkTheme = isDarkMode) {
                val navHostController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        modifier = Modifier.fillMaxSize(),
                        navHostController = navHostController,
                    )
                }
            }
        }
    }
}
