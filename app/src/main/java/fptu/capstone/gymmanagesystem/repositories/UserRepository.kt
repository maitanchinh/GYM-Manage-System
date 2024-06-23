package fptu.capstone.gymmanagesystem.repositories


class UserRepository() {

//    private val Context.dataStore by preferencesDataStore(name = "user_prefs")
//    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    fun login(username: String, password: String): Boolean {
        return username == "" && password == ""
    }

}