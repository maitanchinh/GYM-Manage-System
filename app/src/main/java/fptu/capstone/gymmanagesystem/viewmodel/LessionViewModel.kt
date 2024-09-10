package fptu.capstone.gymmanagesystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Lesson
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.repositories.LessonRepository
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.utils.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(private val lessonRepository: LessonRepository) : ViewModel() {
    private val _lessons = MutableStateFlow<DataState<Response<Lesson>>>(DataState.Idle)
    val lessons : StateFlow<DataState<Response<Lesson>>> = _lessons

    fun getLessons(filterRequestBody: FilterRequestBody) {
       viewModelScope.launch {
            _lessons.value = DataState.Loading
            try {
                val response: Response<Lesson> = lessonRepository.getLessons(filterRequestBody)
                _lessons.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                _lessons.value = DataState.Error(Message.FETCH_DATA_FAILURE.message)
            }
       }
    }
}