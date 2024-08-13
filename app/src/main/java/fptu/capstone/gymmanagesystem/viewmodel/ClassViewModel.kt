package fptu.capstone.gymmanagesystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fptu.capstone.gymmanagesystem.model.ClassMember
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.GClass
import fptu.capstone.gymmanagesystem.model.Lesson
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.repositories.ClassRepository
import fptu.capstone.gymmanagesystem.repositories.LessonRepository
import fptu.capstone.gymmanagesystem.repositories.MemberRepository
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.utils.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassViewModel @Inject constructor(
    private val classRepository: ClassRepository,
    private val lessonRepository: LessonRepository,
    private val memberRepository: MemberRepository
) :
    ViewModel() {

    //    private val _classes = MutableStateFlow(Classes())
    private val _classes = MutableStateFlow<DataState<Response<GClass>>>(DataState.Idle)
    private val _class = MutableStateFlow(GClass())
    private val _lessons = MutableStateFlow(Response<Lesson>())
    private val _classMembers = MutableStateFlow<DataState<Response<ClassMember>>>(DataState.Idle)
    private val _classInDate = MutableStateFlow<DataState<Response<GClass>>>(DataState.Idle)

    //    val classes: StateFlow<Classes> = _classes
    val classes: StateFlow<DataState<Response<GClass>>> = _classes
    val gClass: StateFlow<GClass> = _class
    val lessons: StateFlow<Response<Lesson>> = _lessons
    val classMembers: StateFlow<DataState<Response<ClassMember>>> = _classMembers
    val classInDate: StateFlow<DataState<Response<GClass>>> = _classInDate

    init {
        fetchClasses(FilterRequestBody())
    }

    fun refreshClasses() {
        fetchClasses(FilterRequestBody())
    }

    private fun fetchClasses(filterRequestBody: FilterRequestBody) {
        viewModelScope.launch {
            _classes.value = DataState.Loading
            try {
                val response: Response<GClass> = classRepository.getClasses(filterRequestBody)
                _classes.value = DataState.Success(response)
//                _classes.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at fetchClasses: ${Message.FETCH_DATA_FAILURE.message}")
                _classes.value = DataState.Error(Message.FETCH_DATA_FAILURE.message)
            }
        }
    }

    fun fetchLessons(filterRequestBody: FilterRequestBody) {
        viewModelScope.launch {
            try {
                val response = lessonRepository.getLessons(filterRequestBody)
                _lessons.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at fetchLessons: ${Message.FETCH_DATA_FAILURE.message}")
            }
        }
    }

    fun fetchMembers(filterRequestBody: FilterRequestBody) {
        viewModelScope.launch {
            _classMembers.value = DataState.Loading
            try {
                val response = memberRepository.getMembers(filterRequestBody)
                _classMembers.value = DataState.Success(response)
                println("Response: $response")
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at fetchMembers: ${Message.FETCH_DATA_FAILURE.message}")
                _classMembers.value = DataState.Error(Message.FETCH_DATA_FAILURE.message)
            }
        }
    }

    fun fetchClassesEnrolled(filterRequestBody: FilterRequestBody) {
        viewModelScope.launch {
            _classes.value = DataState.Loading
            try {
                val response: Response<GClass> = classRepository.getClassesEnrolled(filterRequestBody)
                _classes.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at fetchClassesEnrolled: ${Message.FETCH_DATA_FAILURE.message}")
                _classes.value = DataState.Error(Message.FETCH_DATA_FAILURE.message)
            }
        }
    }

    fun fetchClassesInDate(filterRequestBody: FilterRequestBody) {
        viewModelScope.launch {
            _classInDate.value = DataState.Loading
            try {
                val response: Response<GClass> = classRepository.getClassesEnrolled(filterRequestBody)
                _classInDate.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at fetchClassesInDate: ${Message.FETCH_DATA_FAILURE.message}")
                _classInDate.value = DataState.Error(Message.FETCH_DATA_FAILURE.message)
            }
        }
    }
}