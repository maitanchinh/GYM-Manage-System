package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.Course
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CourseApiService {
    @POST("courses/filter")
    suspend fun getCourses(@Body filterRequestBody: FilterRequestBody) : Response<Course>

    @GET("courses/{id}")
    suspend fun getCourseById(@Path("id") id: String): Course
}