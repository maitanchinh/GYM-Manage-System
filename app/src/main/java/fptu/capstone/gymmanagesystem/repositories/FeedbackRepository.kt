package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.network.FeedbackApiService
import javax.inject.Inject

class FeedbackRepository @Inject constructor(private val feedbackApiService: FeedbackApiService) {
    suspend fun getFeedbacks(filterRequestBody: FilterRequestBody) = feedbackApiService.getFeedbacks(filterRequestBody)
}