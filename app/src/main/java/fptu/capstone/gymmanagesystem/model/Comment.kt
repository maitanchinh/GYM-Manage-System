package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id") var id: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("classCommunicationId") var classCommunicationId: String? = null,
    @SerializedName("createAt") var createAt: String? = null
)
