package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("id"        ) var id        : String? = null,
    @SerializedName("name"      ) var name      : String? = null,
    @SerializedName("avatarUrl" ) var avatarUrl : String? = null,
    @SerializedName("status"    ) var status    : String? = null
)
