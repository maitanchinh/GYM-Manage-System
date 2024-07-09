package fptu.capstone.gymmanagesystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fptu.capstone.gymmanagesystem.model.ClassMembers
import fptu.capstone.gymmanagesystem.model.Classes
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.GClass
import fptu.capstone.gymmanagesystem.model.Lessons
import fptu.capstone.gymmanagesystem.repositories.ClassRepository
import fptu.capstone.gymmanagesystem.repositories.LessonRepository
import fptu.capstone.gymmanagesystem.repositories.MemberRepository
import fptu.capstone.gymmanagesystem.utils.DataState
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
    private val _classes = MutableStateFlow<DataState<Classes>>(DataState.Loading)
    private val _class = MutableStateFlow(GClass())
    private val _lessons = MutableStateFlow(Lessons())
    private val _classMembers = MutableStateFlow(ClassMembers())

    //    val classes: StateFlow<Classes> = _classes
    val classes: StateFlow<DataState<Classes>> = _classes
    val gClass: StateFlow<GClass> = _class
    val lessons: StateFlow<Lessons> = _lessons
    val classMembers: StateFlow<ClassMembers> = _classMembers

    init {
        fetchClasses(FilterRequestBody())
    }

    fun refreshClasses() {
        fetchClasses(FilterRequestBody())
    }

    private fun fetchClasses(filterRequestBody: FilterRequestBody) {
        viewModelScope.launch {
            try {
                val response: Classes = classRepository.getClasses(filterRequestBody)
                _classes.value = DataState.Success(response)
//                _classes.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at fetchClasses: ${e.message}")
                _classes.value = DataState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun fetchLessons(filterRequestBody: FilterRequestBody) {
        viewModelScope.launch {
            try {
                val response = lessonRepository.getLessons(filterRequestBody)
                println("Response: $response")
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error: ${e.message}")
            }
        }
    }

    fun fetchMembers(filterRequestBody: FilterRequestBody) {
        viewModelScope.launch {
            try {
                val response = memberRepository.getMembers(filterRequestBody)
                _classMembers.value = response
                println("Response: $response")
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at fetchMembers: ${e.message}")
            }
        }
    }
}