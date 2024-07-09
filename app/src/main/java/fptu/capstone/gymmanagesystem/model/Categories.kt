package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("pagination") var pagination: Pagination? = Pagination(),
    @SerializedName("data") var categories: ArrayList<CourseCategory> = arrayListOf()
)
