package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class InquiryResponse(
    @SerializedName("id") var id: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("inquiryId") var inquiryId: String? = null,
    @SerializedName("staffId") var staffId: String? = null,
    @SerializedName("createAt") var createAt: String? = null,
)
