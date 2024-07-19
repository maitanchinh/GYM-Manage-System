package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.model.User
import fptu.capstone.gymmanagesystem.network.UserApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import javax.inject.Inject


class UserRepository @Inject constructor(private val userApiService: UserApiService) {
    suspend fun getUserById(id: String) = userApiService.getUserById(id)

    suspend fun signUp( email: String, password: String, name: String) : User {
        val emailPart = email.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val passwordPart = password.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val namePart = name.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        return  userApiService.signUp(email = emailPart, password = passwordPart, name = namePart)
    }
}