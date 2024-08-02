package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.User
import fptu.capstone.gymmanagesystem.network.MemberApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class MemberRepository @Inject constructor(private val memberApiService: MemberApiService) {
    suspend fun getMembers(filterRequestBody: FilterRequestBody) =
        memberApiService.getMembers(filterRequestBody)


}