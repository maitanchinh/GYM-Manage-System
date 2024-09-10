package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("id"       ) var id       : String?  = null,
    @SerializedName("amount"   ) var amount   : Int?     = null,
    @SerializedName("status"   ) var status   : String?  = null,
    @SerializedName("createAt" ) var createAt : String?  = null,
    @SerializedName("member"   ) var member   : Member?  = Member(),
    @SerializedName("package"  ) var pack  : Package? = Package()
)
