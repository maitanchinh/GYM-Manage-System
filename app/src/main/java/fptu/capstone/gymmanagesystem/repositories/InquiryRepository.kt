package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.InquiryRequestBody
import fptu.capstone.gymmanagesystem.network.InquiryApiService
import javax.inject.Inject

class InquiryRepository @Inject constructor(private val inquiryApiService: InquiryApiService) {
    suspend fun getInquiries(filterRequestBody: FilterRequestBody) = inquiryApiService.getInquiries(filterRequestBody)

    suspend fun getInquiryDetail(id: String) = inquiryApiService.getInquiryDetail(id)

    suspend fun createInquiry(inquiryRequestBody: InquiryRequestBody) = inquiryApiService.createInquiry(inquiryRequestBody)

    suspend fun deleteInquiry(id: String) = inquiryApiService.deleteInquiry(id)
}