package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.Feedback
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.utils.RequiresAuth
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedbackApiService {
    @POST("slot-feedbacks/filter")
    suspend fun getFeedbacks(@Body filter: FilterRequestBody): Response<Feedback>

    @RequiresAuth
    @POST("slot-feedbacks/{slotId}")
    suspend fun feedback(@Path("slotId") slotId: String, @Body feedback: FeedbackRequestBody): Feedback
}

data class FeedbackRequestBody(
    val score: Int,
    val message: String
)