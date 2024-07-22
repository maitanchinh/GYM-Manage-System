package fptu.capstone.gymmanagesystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fptu.capstone.gymmanagesystem.model.Feedback
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.repositories.FeedbackRepository
import fptu.capstone.gymmanagesystem.utils.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(private val feedbackRepository: FeedbackRepository) : ViewModel() {
    private val _feedbacks = MutableStateFlow<DataState<Response<Feedback>>>(DataState.Idle)
    val feedbacks: StateFlow<DataState<Response<Feedback>>> = _feedbacks

    fun getFeedbacks(filterRequestBody: FilterRequestBody) {
        viewModelScope.launch {
            _feedbacks.value = DataState.Loading
            try {
                val response: Response<Feedback> = feedbackRepository.getFeedbacks(filterRequestBody)
                _feedbacks.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at getFeedbacks: ${e.message}")
                _feedbacks.value = DataState.Error(e.message ?: "Unknown error")
            }
        }
    }
}