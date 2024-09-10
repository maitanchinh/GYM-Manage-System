package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.model.Transaction
import retrofit2.http.Body
import retrofit2.http.POST

interface TransactionApiService {
    @POST("transations/filter")
    suspend fun getTransactions(@Body filterRequestBody: FilterRequestBody): Response<Transaction>
}