package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class Course(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("thumbnailUrl") var thumbnailUrl: String? = null,
    @SerializedName("totalSlot") var totalLesson: Int? = null,
    @SerializedName("totalMember") var totalMember: Int? = null,
    @SerializedName("createAt") var createAt: String? = null,
    @SerializedName("lessonTime") var lessonTime: String? = null,
    @SerializedName("category") var category: CourseCategory? = CourseCategory(),
    @SerializedName("classes") var classes: ArrayList<GClass> = arrayListOf()
)
