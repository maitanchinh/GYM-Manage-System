package fptu.capstone.gymmanagesystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fptu.capstone.gymmanagesystem.model.Attendance
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.repositories.AttendanceRepository
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.utils.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(private val attendanceRepository: AttendanceRepository) :
    ViewModel() {
    private val _attendances = MutableStateFlow<DataState<Response<Attendance>>>(DataState.Idle)
    val attendances: StateFlow<DataState<Response<Attendance>>> = _attendances

    fun fetchAttendances(filterRequestBody: FilterRequestBody) {
        viewModelScope.launch {
            _attendances.value = DataState.Loading
            try {
                val response: Response<Attendance> =
                    attendanceRepository.getAttendances(filterRequestBody)
                _attendances.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at fetchAttendances: ${Message.FETCH_DATA_FAILURE.message}")
                _attendances.value = DataState.Error(Message.FETCH_DATA_FAILURE.message)
            }
        }
    }
}