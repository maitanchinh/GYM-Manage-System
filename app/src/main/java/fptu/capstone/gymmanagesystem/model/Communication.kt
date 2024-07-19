package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class Communication(
    @SerializedName("id") var id: String? = null,
    @SerializedName("imageUrl") var imageUrl: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("createAt") var createAt: String? = null,
    @SerializedName("classCommunicationComments") var classCommunicationComments: ArrayList<Comment> = arrayListOf(),
    @SerializedName("member") var member: User? = User(),
    @SerializedName("trainer") var trainer: User? = User()
){
    fun getUser(): User? {
        return member ?: trainer
    }
}
