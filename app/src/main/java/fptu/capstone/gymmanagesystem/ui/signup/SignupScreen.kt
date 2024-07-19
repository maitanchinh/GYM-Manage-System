package fptu.capstone.gymmanagesystem.ui.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.component.LargeButton
import fptu.capstone.gymmanagesystem.ui.component.TextField
import fptu.capstone.gymmanagesystem.ui.navigation.BottomNavItem
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.viewmodel.UserViewModel

@Composable
fun SignupScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val context = LocalContext.current
    val userState = userViewModel.userState.collectAsState()
    val name by userViewModel.name.collectAsState()
    val email by userViewModel.email.collectAsState()
    val password by userViewModel.password.collectAsState()
    val confirmPassword by userViewModel.confirmPassword.collectAsState()

    LaunchedEffect(key1 = userState.value, block = {
        if (userState.value is DataState.Success) {
            Toast.makeText(context, "Sign up successfully", Toast.LENGTH_SHORT).show()
            navController.navigate(BottomNavItem.Profile.route)
        }
    })
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Sign Up", style = MaterialTheme.typography.headlineLarge)
        Gap.k32.Height()
        TextField(label = "Name", value = name, onTextChange = userViewModel::onNameChange)
        Gap.k8.Height()
        TextField(label = "Email", value = email, onTextChange = userViewModel::onEmailChange)
        Gap.k8.Height()
        TextField(label = "Password", value = password, visualTransformation = PasswordVisualTransformation(), onTextChange = userViewModel::onPasswordChange)
        Gap.k8.Height()
        TextField(label = "Confirm Password", value = confirmPassword, visualTransformation = PasswordVisualTransformation(), onTextChange = userViewModel::onConfirmPasswordChange)
        Gap.k8.Height()
        if (password.isNotEmpty() && confirmPassword.isNotEmpty() && password != confirmPassword) {
            Text(text = "Password and Confirm Password must be the same", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
        }
//        TextField(label = "Gender", value = "", onTextChange = {})
//        Gap.k8.Height()
//        TextField(label = "Phone", value = "", onTextChange = {})
//        Gap.k8.Height()
//        TextField(label = "Address", value = "", onTextChange = {})
        Gap.k32.Height()
        LargeButton(text = "Sign Up", isLoading = userState.value is DataState.Loading, enabled = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && password == confirmPassword, onClick = {
            userViewModel.signup( email = email, password = password, name = name)})
        Gap.k16.Height()
        when(userState.value) {
            is DataState.Error -> {
                val error = (userState.value as DataState.Error).message
                Text(text = error)
            }
            else -> {}
        }
    }
}