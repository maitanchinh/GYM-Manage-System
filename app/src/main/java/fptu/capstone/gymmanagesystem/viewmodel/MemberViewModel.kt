package fptu.capstone.gymmanagesystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fptu.capstone.gymmanagesystem.repositories.MemberRepository
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.utils.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(private val memberRepository: MemberRepository) : ViewModel(){
    private val _urlPayment = MutableStateFlow<DataState<Map<String, String>>>(DataState.Idle)
    val urlPayment : StateFlow<DataState<Map<String, String>>> = _urlPayment
    private val _paymentState = MutableStateFlow(false)
    val paymentState : StateFlow<Boolean> = _paymentState

    fun buyPackage(packageId: String){
       viewModelScope.launch {
           _urlPayment.value = DataState.Loading
           try {
               val response = memberRepository.buyPackage(packageId)
               _urlPayment.value = DataState.Success(response)
               println("Success at buyPackage: $response")
           } catch (e: HttpException) {
               e.printStackTrace()
               println("Error at buyPackage: ${Message.FETCH_DATA_FAILURE.message}")
               _urlPayment.value = DataState.Error(if (e.code() ==  400) Message.PAYMENT_PENDING.message else Message.ERROR_NETWORK.message)
           }
       }
    }

    fun setPaymentState(state: Boolean){
        _paymentState.value = state
    }
}