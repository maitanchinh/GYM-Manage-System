package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class Wishlist(
    @SerializedName("id") var id: String? = null,
    @SerializedName("createAt") var createAt: String? = null,
    @SerializedName("course") var course: Course? = Course(),
    @SerializedName("member") var member: Member? = Member()
)
