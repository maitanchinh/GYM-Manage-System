package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.ClassMember
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberApiService {
    @POST("class-members/filter")
    suspend fun getMembers(@Body filterRequestBody: FilterRequestBody): Response<ClassMember>

}