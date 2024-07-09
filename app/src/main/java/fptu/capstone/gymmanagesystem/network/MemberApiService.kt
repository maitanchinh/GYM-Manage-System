package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.ClassMembers
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberApiService {
    @POST("class-members/filter")
    suspend fun getMembers(@Body filterRequestBody: FilterRequestBody): ClassMembers

}