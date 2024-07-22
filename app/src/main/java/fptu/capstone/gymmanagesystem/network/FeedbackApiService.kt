package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.Feedback
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbackApiService {
    @POST("slot-feedbacks/filter")
    suspend fun getFeedbacks(@Body filter: FilterRequestBody): Response<Feedback>
}