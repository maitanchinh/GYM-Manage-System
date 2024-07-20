package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.network.AttendanceApiService
import javax.inject.Inject

class AttendanceRepository @Inject constructor(private val attendanceApiService: AttendanceApiService){
    suspend fun getAttendances(filterRequestBody: FilterRequestBody) = attendanceApiService.getAttendances(filterRequestBody)
}