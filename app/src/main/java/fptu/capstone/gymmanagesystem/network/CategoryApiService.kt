package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.CourseCategory
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CategoryApiService {
    @POST("categories/filter")
    suspend fun getCategories(@Body filterRequestBody: FilterRequestBody): Response<CourseCategory>

}