package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.Course
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import fptu.capstone.gymmanagesystem.model.Wishlist
import fptu.capstone.gymmanagesystem.utils.RequiresAuth
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CourseApiService {
    @POST("courses/filter")
    suspend fun getCourses(@Body filterRequestBody: FilterRequestBody) : Response<Course>

    @GET("courses/{id}")
    suspend fun getCourseById(@Path("id") id: String): Course

    @RequiresAuth
    @POST("wishlists/{courseId}")
    suspend fun addWishlist(@Path("courseId") courseId: String) : Wishlist

    @POST("wishlists/filter")
    suspend fun getWishlists(@Body filterRequestBody: FilterRequestBody) : Response<Wishlist>

    @RequiresAuth
    @DELETE("wishlists/{courseId}/get-out")
    suspend fun removeWishlist(@Path("courseId") courseId: String) : Wishlist
}