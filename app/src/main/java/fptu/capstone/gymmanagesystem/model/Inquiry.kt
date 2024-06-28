package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class Inquiry(
    @SerializedName("id") var id: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("member") var member: User? = null,
    @SerializedName("createAt") var createAt: String? = null,
    @SerializedName("inquiryResponse") var inquiryResponse: InquiryResponse? = InquiryResponse()
)

data class InquiryRequestBody(
    @SerializedName("title") var title: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("memberId") var memberId: String? = null,
)