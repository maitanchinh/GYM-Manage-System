package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.network.CourseApiService
import javax.inject.Inject

class CourseRepository @Inject constructor(private val courseApiService: CourseApiService) {
    suspend fun getCourses(filterRequestBody: FilterRequestBody) = courseApiService.getCourses(filterRequestBody)

    suspend fun getCourseById(id: String) = courseApiService.getCourseById(id)
}