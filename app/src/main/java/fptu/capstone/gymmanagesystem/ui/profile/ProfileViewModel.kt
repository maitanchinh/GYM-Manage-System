package fptu.capstone.gymmanagesystem.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import fptu.capstone.gymmanagesystem.utils.SettingDataStore
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application){
    private val context = application.applicationContext;
    val darkMode = SettingDataStore.getDarkMode(context).asLiveData()

    fun onDarkModeSwitchChanged(isDarkMode: Boolean) {
        viewModelScope.launch { SettingDataStore.saveDarkMode(context, isDarkMode) }
    }
}

//class SettingsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return ProfileViewModel(application) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
