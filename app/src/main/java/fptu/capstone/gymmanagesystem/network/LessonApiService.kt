package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Lesson
import fptu.capstone.gymmanagesystem.model.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface LessonApiService {
    @POST("slots/filter")
    suspend fun getLessons(@Body filterRequestBody: FilterRequestBody) : Response<Lesson>

    @POST("slots/{id}")
    suspend fun getLessonsByClassId(@Path("id") id: String): Lesson
}