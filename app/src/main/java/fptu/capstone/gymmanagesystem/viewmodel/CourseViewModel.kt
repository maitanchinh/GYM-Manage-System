package fptu.capstone.gymmanagesystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fptu.capstone.gymmanagesystem.model.Course
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.repositories.CourseRepository
import fptu.capstone.gymmanagesystem.utils.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(private val courseRepository: CourseRepository) : ViewModel() {
    private val _courses = MutableStateFlow<DataState<Response<Course>>>(DataState.Idle)
    val courses: StateFlow<DataState<Response<Course>>> = _courses
    private val _course = MutableStateFlow<DataState<Course>>(DataState.Idle)
    val course: StateFlow<DataState<Course>> = _course

    init {
        fetchCourses(FilterRequestBody())
    }

    fun refreshCourses(filterRequestBody: FilterRequestBody) {
        fetchCourses(filterRequestBody)
    }

    fun fetchCourses(filterRequestBody: FilterRequestBody) {
        viewModelScope.launch {
            _courses.value = DataState.Loading
            try {
                val response = courseRepository.getCourses(filterRequestBody)
                _courses.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at fetchCourses: ${e.message}")
                _courses.value = DataState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getCourseById(id: String) {
        viewModelScope.launch {
            _course.value = DataState.Loading
            try {
                val response = courseRepository.getCourseById(id)
                _course.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at getCourseById: ${e.message}")
                _course.value = DataState.Error(e.message ?: "Unknown error")
            }
        }
    }
}