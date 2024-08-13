package fptu.capstone.gymmanagesystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fptu.capstone.gymmanagesystem.model.CourseCategory
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.repositories.CategoryRepository
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.utils.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoryRepository: CategoryRepository) : ViewModel(){
    private val _categories = MutableStateFlow<DataState<Response<CourseCategory>>>(DataState.Idle)
    val categories : StateFlow<DataState<Response<CourseCategory>>> = _categories

    init {
        fetchCategories(FilterRequestBody())
    }

    private fun fetchCategories(filterRequestBody: FilterRequestBody){
        viewModelScope.launch {
            _categories.value = DataState.Loading
            try {
                val response = categoryRepository.getCategories(filterRequestBody)
                _categories.value = DataState.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                _categories.value = DataState.Error(Message.FETCH_DATA_FAILURE.message)
            }
        }
    }
}