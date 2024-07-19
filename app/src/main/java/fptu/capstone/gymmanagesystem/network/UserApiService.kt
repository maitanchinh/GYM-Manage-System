package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.User
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface UserApiService {
    @GET("members/{id}")
    suspend fun getUserById(@Path("id") id: String?): User

    @Multipart
    @POST("members")
    suspend fun signUp(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("name") name: RequestBody
    ): User
}