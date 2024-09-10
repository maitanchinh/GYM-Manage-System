package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class Package(
    @SerializedName("id"       ) var id       : String? = null,
    @SerializedName("name"     ) var name     : String? = null,
    @SerializedName("price"    ) var price    : Int?    = null,
    @SerializedName("duration" ) var duration : Int?    = null,
    @SerializedName("status"   ) var status   : String? = null,
    @SerializedName("createAt" ) var createAt : String? = null,
    @SerializedName("rank"     ) var rank     : Rank?   = Rank()
)
