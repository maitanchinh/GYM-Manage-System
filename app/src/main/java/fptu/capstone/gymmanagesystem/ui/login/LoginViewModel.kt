package fptu.capstone.gymmanagesystem.ui.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fptu.capstone.gymmanagesystem.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _username = mutableStateOf("")
    val username: State<String> = _username
    private val _password = mutableStateOf("")
    val password: State<String> = _password
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    private val _isLoginSuccess = mutableStateOf(false)
    val isLoginSuccess: State<Boolean> = _isLoginSuccess
    private val _isLogoutSuccess = mutableStateOf(false)
    val isLogoutSuccess: State<Boolean> = _isLogoutSuccess
    private val sessionManager = SessionManager.getInstance()
    fun onUsernameChange(username: String) {
        _username.value = username
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun login() {
        viewModelScope.launch {
            _isLoading.value = true
            kotlinx.coroutines.delay(2000)
            val result = userRepository.login(_username.value, _password.value)
            _isLoading.value = false
            _isLoginSuccess.value = result
            if (result) {
                sessionManager.saveAccessToken("123456")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _isLoading.value = true
            sessionManager.clearAccessToken()
            _isLoading.value = false
            _isLoginSuccess.value = false
            _isLogoutSuccess.value = true
        }
    }
}