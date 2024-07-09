package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class ClassMembers(
    @SerializedName("pagination" ) var pagination : Pagination?     = Pagination(),
    @SerializedName("data"       ) var classMembers       : ArrayList<ClassMember> = arrayListOf()
)
