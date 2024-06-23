package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.network.ClassApiService
import javax.inject.Inject

class ClassRepository @Inject constructor(private val classApiService: ClassApiService){
    suspend fun getClasses() = classApiService.getClasses()

    suspend fun getClassById(id: String) = classApiService.getClassById(id)
}