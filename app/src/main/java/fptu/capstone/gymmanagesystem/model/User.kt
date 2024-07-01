package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") var id: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("avatarUrl") var avatarUrl: String? = null,
    @SerializedName("createAt") var createAt: String? = null,
    @SerializedName("validDate") var validDate: String? = null
)

data class SignUpRequest(
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("name") var name: String? = null
)