package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.GClass
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.utils.RequiresAuth
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ClassApiService{
    @POST("classes/filter")
    suspend fun getClasses(@Body filterRequestBody: FilterRequestBody): Response<GClass>

    @GET("classes/{id}")
    suspend fun getClassById(@Path("id") id: String): GClass

    @RequiresAuth
    @POST("members/classes")
    suspend fun getClassesEnrolled(@Body filterRequestBody: FilterRequestBody): Response<GClass>
}