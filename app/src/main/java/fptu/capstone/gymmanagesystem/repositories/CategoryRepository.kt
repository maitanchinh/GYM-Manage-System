package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.network.CategoryApiService
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryApiService: CategoryApiService){
    suspend fun getCategories(filterRequestBody: FilterRequestBody) = categoryApiService.getCategories(filterRequestBody)
}