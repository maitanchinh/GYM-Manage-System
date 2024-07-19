package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Inquiry
import fptu.capstone.gymmanagesystem.model.InquiryRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InquiryApiService {
    @POST("inquiries/filter")
    suspend fun getInquiries(@Body filterRequestBody: FilterRequestBody) : Response<Inquiry>

    @GET("inquiries/{id}")
    suspend fun getInquiryDetail(@Path("id") id: String) : Inquiry

    @POST("inquiries")
    suspend fun createInquiry(@Body inquiryRequestBody: InquiryRequestBody) : Inquiry

    @DELETE("inquiries/{id}")
    suspend fun deleteInquiry(@Path("id") id: String) : Inquiry
}