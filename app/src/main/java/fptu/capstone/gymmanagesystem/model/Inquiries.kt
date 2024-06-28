package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class Inquiries(
    @SerializedName("pagination") var pagination: Pagination? = Pagination(),
    @SerializedName("data") var inquiries: ArrayList<Inquiry> = arrayListOf()
)
