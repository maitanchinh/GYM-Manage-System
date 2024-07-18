package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class GClass(

    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("thumbnailUrl") var thumbnailUrl: String? = null,
    @SerializedName("trainer") var trainer: Trainer? = Trainer(),
    @SerializedName("totalLesson") var totalLesson: Int? = 0,
    @SerializedName("from") var from: String? = null,
    @SerializedName("to") var to: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("totalMember") var totalMember: Int? = 0,
    @SerializedName("lessonCount") var lessonCount: Int? = 0,
    @SerializedName("participant") var participant: Int? = 0,
    var lessons: List<Lesson> = listOf(),
)