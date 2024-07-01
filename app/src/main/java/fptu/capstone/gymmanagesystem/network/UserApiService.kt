package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.SignUpRequest
import fptu.capstone.gymmanagesystem.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {
    @GET("members/{id}")
    suspend fun getUserById(@Path("id") id: String?) : User

    @POST("members")
    suspend fun signUp(@Body user: SignUpRequest) : User
}