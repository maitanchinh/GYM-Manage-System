package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.model.Transaction
import fptu.capstone.gymmanagesystem.network.TransactionApiService
import javax.inject.Inject

class TransactionRepository @Inject constructor(private val transactionApiService: TransactionApiService) {
    suspend fun getTransactions(filterRequestBody: FilterRequestBody): Response<Transaction> {
        return transactionApiService.getTransactions(filterRequestBody)
    }
}