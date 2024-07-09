package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.network.MemberApiService
import javax.inject.Inject

class MemberRepository @Inject constructor(private val memberApiService: MemberApiService) {
    suspend fun getMembers(filterRequestBody: FilterRequestBody) = memberApiService.getMembers(filterRequestBody)
}