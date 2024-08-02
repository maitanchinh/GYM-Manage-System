package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.model.User
import fptu.capstone.gymmanagesystem.network.UserApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


class UserRepository @Inject constructor(private val userApiService: UserApiService) {
    suspend fun getUserById(id: String) = userApiService.getUserById(id)

    suspend fun signUp( email: String, password: String, name: String) : User {
        val emailPart = email.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val passwordPart = password.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val namePart = name.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        return  userApiService.signUp(email = emailPart, password = passwordPart, name = namePart)
    }

    suspend fun updateUser(
        id: String,
        name: String?,
        phone: String?,
        gender: String?,
        dateOfBirth: String?,
        status: String?,
        avatar: File?
    ): User {
        val namePart = name?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val phonePart = phone?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val genderPart = gender?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val dateOfBirthPart = dateOfBirth?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val statusPart = status?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val avatarPart = avatar?.let {
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), it)
            MultipartBody.Part.createFormData("avatar", it.name, requestFile)
        }

        return userApiService.updateUser(
            id = id,
            name = namePart,
            phone = phonePart,
            gender = genderPart,
            dateOfBirth = dateOfBirthPart,
            status = statusPart,
            avatar = avatarPart
        )

    }
}