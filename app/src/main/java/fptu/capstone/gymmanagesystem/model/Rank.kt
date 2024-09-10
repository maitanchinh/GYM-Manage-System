package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class Rank(
    @SerializedName("id"         ) var id         : String? = null,
    @SerializedName("name"       ) var name       : String? = null,
    @SerializedName("permission" ) var permission : Int?    = null
)
