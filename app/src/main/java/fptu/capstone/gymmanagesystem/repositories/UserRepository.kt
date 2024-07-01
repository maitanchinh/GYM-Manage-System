package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.model.SignUpRequest
import fptu.capstone.gymmanagesystem.network.UserApiService
import javax.inject.Inject


class UserRepository @Inject constructor(private val userApiService: UserApiService) {
    suspend fun getUserById(id: String) = userApiService.getUserById(id)

    suspend fun signUp(user: SignUpRequest) = userApiService.signUp(user)
}