package fptu.capstone.gymmanagesystem.model

import com.google.gson.annotations.SerializedName

data class FilterRequestBody(
    @SerializedName("search") var search: String? = null,
    @SerializedName("classId") var classId: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("from") var from: String? = null,
    @SerializedName("to") var to: String? = null,
    @SerializedName("categoryId") var categoryId: String? = null,
    @SerializedName("orderBy") var orderBy: String? = null,
    @SerializedName("isAscending") var isAscending: Boolean? = null,
    @SerializedName("pagination") var pagination: Pagination? = Pagination()
)
