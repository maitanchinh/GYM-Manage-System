package fptu.capstone.gymmanagesystem.network

import fptu.capstone.gymmanagesystem.model.Attendance
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.model.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AttendanceApiService {
    @POST("attendances/filter")
    suspend fun getAttendances(@Body filterRequestBody: FilterRequestBody): Response<Attendance>
}