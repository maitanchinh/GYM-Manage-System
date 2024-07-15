package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.network.ClassApiService
import javax.inject.Inject

class ClassRepository @Inject constructor(private val classApiService: ClassApiService){
    suspend fun getClasses(filterRequestBody: FilterRequestBody) = classApiService.getClasses(filterRequestBody)

    suspend fun getClassById(id: String) = classApiService.getClassById(id)

    suspend fun getClassesEnrolled(filterRequestBody: FilterRequestBody) = classApiService.getClassesEnrolled(filterRequestBody)
}