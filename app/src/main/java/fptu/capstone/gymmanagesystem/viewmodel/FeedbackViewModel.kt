package fptu.capstone.gymmanagesystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fptu.capstone.gymmanagesystem.model.Feedback
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.network.FeedbackRequestBody
import fptu.capstone.gymmanagesystem.repositories.FeedbackRepository
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.utils.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(private val feedbackRepository: FeedbackRepository) : ViewModel() {
    private val _feedbacks = MutableStateFlow<DataState<Response<Feedback>>>(DataState.Idle)
    val feedbacks: StateFlow<DataState<Response<Feedback>>> = _feedbacks
    private val _feedback = MutableStateFlow<DataState<Feedback>>(DataState.Idle)
    val feedback: StateFlow<DataState<Feedback>> = _feedback
    private val _isShowFeedbackDialog = MutableStateFlow(false)
    val isShowFeedbackDialog: MutableStateFlow<Boolean> = _isShowFeedbackDialog
    private val _feedbackMessage = MutableStateFlow("")
    val feedbackMessage: MutableStateFlow<String> = _feedbackMessage
    private val _feedbackSlotId = MutableStateFlow("")
    val feedbackSlotId: MutableStateFlow<String> = _feedbackSlotId
    private val _feedbackScore = MutableStateFlow(0)
    val feedbackScore: MutableStateFlow<Int> = _feedbackScore

    fun getFeedbacks(filterRequestBody: FilterRequestBody) {
        viewModelScope.launch {
            _feedbacks.value = DataState.Loading
            try {
                val response: Response<Feedback> = feedbackRepository.getFeedbacks(filterRequestBody)
                _feedbacks.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at getFeedbacks: ${Message.FETCH_DATA_FAILURE.message}")
                _feedbacks.value = DataState.Error(Message.FETCH_DATA_FAILURE.message)
            }
        }
    }

    fun setShowFeedbackDialog() {
        _isShowFeedbackDialog.value = !_isShowFeedbackDialog.value
    }

    fun setFeedbackMessage(message: String) {
        _feedbackMessage.value = message
    }

    fun setFeedbackSlotId(slotId: String) {
        _feedbackSlotId.value = slotId
    }

    fun setFeedbackScore(score: Int) {
        _feedbackScore.value = score
    }

    fun sendFeedback(feedback: FeedbackRequestBody, slotId: String) {
        viewModelScope.launch {
            _feedback.value = DataState.Loading
            try {
                val response: Feedback = feedbackRepository.feedback(slotId = slotId, feedback = feedback)
                _feedback.value = DataState.Success(response)
            } catch (e: HttpException) {
                e.printStackTrace()
                println("Error at sendFeedback: ${Message.FETCH_DATA_FAILURE.message}")
                _feedback.value = DataState.Error(if (e.code() == 100) "You have already feedback this lesson" else "Unknown error")
            }
        }
    }
}