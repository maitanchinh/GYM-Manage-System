package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class ClassMember(
    @SerializedName("id"     ) var id     : String? = null,
    @SerializedName("status" ) var status : String? = null,
    @SerializedName("member" ) var member : Member? = Member()
)
