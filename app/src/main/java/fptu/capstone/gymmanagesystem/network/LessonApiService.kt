package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Lessons
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface LessonApiService {
    @POST("slots/filter")
    suspend fun getLessons(@Body filterRequestBody: FilterRequestBody) : Lessons

    @POST("slots/{id}")
    suspend fun getLessonsByClassId(@Path("id") id: String): Lessons
}