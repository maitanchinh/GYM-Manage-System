package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Inquiries
import fptu.capstone.gymmanagesystem.model.Inquiry
import fptu.capstone.gymmanagesystem.model.InquiryRequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InquiryApiService {
    @POST("inqueries/filter")
    suspend fun getInquiries(@Body filterRequestBody: FilterRequestBody) : Inquiries

    @GET("inqueries/{id}")
    suspend fun getInquiryDetail(@Path("id") id: String) : Inquiry

    @POST("inqueries")
    suspend fun createInquiry(@Body inquiryRequestBody: InquiryRequestBody) : Inquiry

    @DELETE("inqueries/{id}")
    suspend fun deleteInquiry(@Path("id") id: String) : Inquiry
}