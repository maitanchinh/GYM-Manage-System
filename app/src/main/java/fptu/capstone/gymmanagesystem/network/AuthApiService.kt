package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.AuthResponse
import fptu.capstone.gymmanagesystem.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth")
    suspend fun login(@Body loginRequest: LoginRequest): AuthResponse
}