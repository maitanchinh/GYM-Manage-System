import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class SessionManager(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "user_prefs")
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")

    companion object {
        @Volatile
        private var instance: SessionManager? = null

        fun initialize(context: Context) {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = SessionManager(context.applicationContext)
                    }
                }
            }
        }

        fun getInstance(): SessionManager {
            return instance ?: throw IllegalStateException("SessionManager must be initialized")
        }
    }

    suspend fun saveAccessToken(accessToken: String) {
        try {
            context.dataStore.edit { preferences ->
                preferences[ACCESS_TOKEN_KEY] = accessToken
            }
        } catch (e: IOException) {
            // Handle IOException appropriately
            e.printStackTrace()
        }
    }

    private fun getAccessToken(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[ACCESS_TOKEN_KEY] ?: ""
            }
    }

    fun isLoggedIn(): Flow<Boolean> {
        return getAccessToken().map { it.isNotEmpty() }
    }

    suspend fun clearAccessToken() {
        try {
            kotlinx.coroutines.delay(2000)
            context.dataStore.edit { preferences ->
                preferences[ACCESS_TOKEN_KEY] = ""
            }
        } catch (e: IOException) {
            // Handle IOException appropriately
            e.printStackTrace()
        }
    }
}
