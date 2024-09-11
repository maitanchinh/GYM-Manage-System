package fptu.capstone.gymmanagesystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fptu.capstone.gymmanagesystem.model.Course
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.model.Wishlist
import fptu.capstone.gymmanagesystem.repositories.CourseRepository
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.utils.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(private val courseRepository: CourseRepository) :
    ViewModel() {
    private val _courses = MutableStateFlow<DataState<Response<Course>>>(DataState.Idle)
    val courses: StateFlow<DataState<Response<Course>>> = _courses
    private val _course = MutableStateFlow<DataState<Course>>(DataState.Idle)
    val course: StateFlow<DataState<Course>> = _course
    val myCourses: ArrayList<Course> = ArrayList()
    private val _wishlist = MutableStateFlow<DataState<Wishlist>>(DataState.Idle)
    val wishlist: StateFlow<DataState<Wishlist>> = _wishlist
    private val _wishlists = MutableStateFlow<DataState<Response<Wishlist>>>(DataState.Idle)
    val wishlists: StateFlow<DataState<Response<Wishlist>>> = _wishlists

    init {
        fetchCourses(FilterRequestBody())
    }

    fun refreshCourses(filterRequestBody: FilterRequestBody) {
        fetchCourses(filterRequestBody)
    }

    fun resetWishlist() {
        _wishlist.value = DataState.Idle
    }

    fun addMyCourse(course: Course) {
        if (!myCourses.contains(course))
            myCourses.add(course)
    }

    fun fetchCourses(filterRequestBody: FilterRequestBody) {
        viewModelScope.launch {
            _courses.value = DataState.Loading
            try {
                val response = courseRepository.getCourses(filterRequestBody)
                _courses.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                _courses.value = DataState.Error(Message.FETCH_DATA_FAILURE.message)
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
                println("Error at getCourseById: ${Message.FETCH_DATA_FAILURE.message}")
                _course.value = DataState.Error(Message.FETCH_DATA_FAILURE.message)
            }
        }
    }

    fun fetchWishlists(filterRequestBody: FilterRequestBody) {
        viewModelScope.launch {
            _wishlists.value = DataState.Loading
            try {
                val response = courseRepository.getWishlists(filterRequestBody)
                _wishlists.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at fetchWishlists: ${Message.FETCH_DATA_FAILURE.message}")
                _wishlists.value = DataState.Error(Message.FETCH_DATA_FAILURE.message)
            }
        }
    }

    fun addWishlist(courseId: String) {
        viewModelScope.launch {
            _wishlist.value = DataState.Loading
            try {
                val response = courseRepository.addWishlist(courseId)
                _wishlist.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error at addWishlist: ${Message.FETCH_DATA_FAILURE.message}")
                _wishlist.value = DataState.Error(Message.FETCH_DATA_FAILURE.message)
            }
        }
    }

    fun removeWishlist(courseId: String) {
        viewModelScope.launch {
            _wishlist.value = DataState.Loading
            try {
                val response = courseRepository.removeWishlist(courseId)
                _wishlist.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                _wishlist.value = DataState.Error(Message.FETCH_DATA_FAILURE.message)
            }
        }
    }
}