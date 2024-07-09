package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.network.LessonApiService
import javax.inject.Inject

class LessonRepository @Inject constructor(private val lessonApiService: LessonApiService) {
    suspend fun getLessons(filterRequestBody: FilterRequestBody) = lessonApiService.getLessons(filterRequestBody)
}