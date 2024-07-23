package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class Lesson(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("startTime") var startTime: String? = null,
    @SerializedName("endTime") var endTime: String? = null,
    @SerializedName("classId") var classId: String? = null,
    @SerializedName("feedbackStatus") var feedbackStatus: Boolean? = null,
    var isAttendance: Boolean = false,
    var isPast: Boolean = false,
    var feedback: Feedback? = null
)
