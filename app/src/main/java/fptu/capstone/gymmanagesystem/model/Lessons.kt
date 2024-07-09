package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class Lessons(
    @SerializedName("pagination" ) var pagination : Pagination?     = Pagination(),
    @SerializedName("data"       ) var lessons       : ArrayList<Lesson> = arrayListOf()
)
