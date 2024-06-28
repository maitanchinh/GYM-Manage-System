package fptu.capstone.gymmanagesystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Inquiries
import fptu.capstone.gymmanagesystem.model.Inquiry
import fptu.capstone.gymmanagesystem.model.InquiryRequestBody
import fptu.capstone.gymmanagesystem.repositories.InquiryRepository
import fptu.capstone.gymmanagesystem.utils.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InquiryViewModel @Inject constructor(private val inquiryRepository: InquiryRepository) :
    ViewModel() {
    private val _inquiries = MutableStateFlow<DataState<Inquiries>>(DataState.Idle)
    val inquiries: StateFlow<DataState<Inquiries>> = _inquiries
    private val _inquiry = MutableStateFlow<DataState<Inquiry>>(DataState.Idle)
    val inquiry: StateFlow<DataState<Inquiry>> = _inquiry
    private val _showAddInquiryDialog = MutableStateFlow(false)
    val showAddInquiryDialog: StateFlow<Boolean> = _showAddInquiryDialog
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title
    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    init {
        fetchInquiries(FilterRequestBody())
    }

    fun refreshInquiries() {
        fetchInquiries(FilterRequestBody())
    }

    fun resetInquiries() {
        _inquiries.value = DataState.Idle
    }

    fun resetInquiry() {
        _inquiry.value = DataState.Idle
    }

    private fun fetchInquiries(filterRequestBody: FilterRequestBody) {
        viewModelScope.launch {
            _inquiries.value = DataState.Loading
            try {
                val response = inquiryRepository.getInquiries(filterRequestBody)
                _inquiries.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at fetchInquiries: ${e.message}")
                _inquiries.value = DataState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getInquiryDetail(id: String) {
        viewModelScope.launch {
            _inquiry.value = DataState.Loading
            try {
                val response = inquiryRepository.getInquiryDetail(id)
                _inquiry.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at getInquiryDetail: ${e.message}")
                _inquiry.value = DataState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun showOrHideAddInquiryDialog() {
        _showAddInquiryDialog.value = !_showAddInquiryDialog.value
    }

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setMessage(message: String) {
        _message.value = message
    }

    fun createInquiry(inquiryRequestBody: InquiryRequestBody) {
        viewModelScope.launch {
            _inquiry.value = DataState.Loading
            try {
                val response = inquiryRepository.createInquiry(inquiryRequestBody)
                _inquiry.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at createInquiry: ${e.message}")
                _inquiry.value = DataState.Error(e.message ?: "Unknown error")
            }
        }
    }
}