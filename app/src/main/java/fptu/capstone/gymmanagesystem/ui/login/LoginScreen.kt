package fptu.capstone.gymmanagesystem.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import fptu.capstone.gymmanagesystem.ui.component.LargeButton
import fptu.capstone.gymmanagesystem.ui.component.TextField
import fptu.capstone.gymmanagesystem.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val isLoading by loginViewModel.isLoading
    val isLoginSuccess by loginViewModel.isLoginSuccess
    val isLogoutSuccess by loginViewModel.isLogoutSuccess

    if (isLoginSuccess && !isLogoutSuccess) {
        LaunchedEffect(Unit) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Center
    ) {
        TextField(value = "", label = "Username", onTextChange = loginViewModel::onUsernameChange)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = "", label = "Password", visualTransformation = PasswordVisualTransformation(), onTextChange = loginViewModel::onPasswordChange)
        Spacer(modifier = Modifier.height(32.dp))
        LargeButton(text = "Login", isLoading = isLoading, onClick = loginViewModel::login)
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onSignUpClick) {
            Text(text = "Don't have an account? Register now!")
        }
    }
}