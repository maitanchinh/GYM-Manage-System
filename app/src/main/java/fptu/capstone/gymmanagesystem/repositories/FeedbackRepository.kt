package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.network.FeedbackApiService
import fptu.capstone.gymmanagesystem.network.FeedbackRequestBody
import javax.inject.Inject

class FeedbackRepository @Inject constructor(private val feedbackApiService: FeedbackApiService) {
    suspend fun getFeedbacks(filterRequestBody: FilterRequestBody) = feedbackApiService.getFeedbacks(filterRequestBody)

    suspend fun feedback(slotId: String, feedback: FeedbackRequestBody) = feedbackApiService.feedback(slotId = slotId, feedback = feedback)
}