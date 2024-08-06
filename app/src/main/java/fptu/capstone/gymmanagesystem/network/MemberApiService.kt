package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.ClassMember
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.utils.RequiresAuth
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MemberApiService {
    @POST("class-members/filter")
    suspend fun getMembers(@Body filterRequestBody: FilterRequestBody): Response<ClassMember>

    @RequiresAuth
    @PUT("members/{packageId}/buy-package")
    suspend fun buyPackage(@Path("packageId") packageId: String): Map<String, String>
}

