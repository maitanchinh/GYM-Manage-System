package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.ClassMember
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface MemberApiService {
    @POST("class-members/filter")
    suspend fun getMembers(@Body filterRequestBody: FilterRequestBody): Response<ClassMember>


}
