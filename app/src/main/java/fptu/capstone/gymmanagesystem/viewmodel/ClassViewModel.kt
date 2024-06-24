package fptu.capstone.gymmanagesystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fptu.capstone.gymmanagesystem.model.Classes
import fptu.capstone.gymmanagesystem.model.GClass
import fptu.capstone.gymmanagesystem.repositories.ClassRepository
import fptu.capstone.gymmanagesystem.utils.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassViewModel @Inject constructor(private val classRepository: ClassRepository) :
    ViewModel() {

//    private val _classes = MutableStateFlow(Classes())
    private val _classes = MutableStateFlow<DataState<Classes>>(DataState.Loading)
    private val _class = MutableStateFlow(GClass())
//    val classes: StateFlow<Classes> = _classes
    val classes: StateFlow<DataState<Classes>> = _classes
    val gClass: StateFlow<GClass> = _class

    init {
        fetchClasses()
    }

    fun refreshClasses() {
        fetchClasses()
    }

    private fun fetchClasses() {
        viewModelScope.launch {
            try {
                val response = classRepository.getClasses()
                println("API Response: $response")
                _classes.value = DataState.Success(response)
//                _classes.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error: ${e.message}")
                _classes.value = DataState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getClassById(id: String) {
        viewModelScope.launch {
            try {
                val response = classRepository.getClassById(id)
                _class.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}