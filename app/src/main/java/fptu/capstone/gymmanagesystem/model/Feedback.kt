package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class Feedback(
    @SerializedName("id") var id: String? = null,
    @SerializedName("score") var score: Int? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("createAt") var createAt: String? = null,
    @SerializedName("member") var member: Member? = Member(),
    @SerializedName("slot") var slot: Lesson? = Lesson()
)
