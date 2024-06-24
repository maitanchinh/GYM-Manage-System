package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.model.AuthResponse
import fptu.capstone.gymmanagesystem.model.LoginRequest
import fptu.capstone.gymmanagesystem.network.AuthApiService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authApiService: AuthApiService) {
    suspend fun login(email: String, password: String): AuthResponse {
        val loginRequest = LoginRequest(email, password)
        return authApiService.login(loginRequest)
    }
}