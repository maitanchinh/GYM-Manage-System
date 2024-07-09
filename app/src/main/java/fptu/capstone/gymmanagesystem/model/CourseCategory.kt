package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class CourseCategory(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("createAt") var createAt: String? = null,
    @SerializedName("courses") var courses: ArrayList<String> = arrayListOf()
)
