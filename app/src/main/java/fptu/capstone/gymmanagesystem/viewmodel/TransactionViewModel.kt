package fptu.capstone.gymmanagesystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.model.Transaction
import fptu.capstone.gymmanagesystem.repositories.TransactionRepository
import fptu.capstone.gymmanagesystem.utils.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val transactionRepository: TransactionRepository) : ViewModel() {
    private val _transactions = MutableStateFlow<DataState<Response<Transaction>>>(DataState.Idle)
    val transactions : StateFlow<DataState<Response<Transaction>>> = _transactions

    fun getTransactions(filterRequestBody: FilterRequestBody) {
        viewModelScope.launch {
            try {
                _transactions.value = DataState.Loading
                val response: Response<Transaction> = transactionRepository.getTransactions(filterRequestBody = filterRequestBody)
                _transactions.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                _transactions.value = DataState.Error("Failed to fetch data")
            }
        }
    }
}