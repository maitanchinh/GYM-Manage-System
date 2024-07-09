package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.Classes
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.GClass
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ClassApiService{
    @POST("classes/filter")
    suspend fun getClasses(@Body filterRequestBody: FilterRequestBody): Classes

    @GET("classes/{id}")
    suspend fun getClassById(@Path("id") id: String): GClass
}