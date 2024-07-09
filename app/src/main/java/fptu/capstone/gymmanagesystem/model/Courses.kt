package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class Courses(
    @SerializedName("pagination" ) var pagination : Pagination?     = Pagination(),
    @SerializedName("data"       ) var courses       : ArrayList<Course> = arrayListOf()
)
