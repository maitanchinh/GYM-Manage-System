package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.Categories
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface CategoryApiService {
    @POST("categories/filter")
    suspend fun getCategories(@Body filterRequestBody: FilterRequestBody): Categories

}