package fptu.capstone.gymmanagesystem.ui.schedule.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.viewmodel.ClassViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun WeekCalendar(classViewModel: ClassViewModel = hiltViewModel()) {
    val dateFormatter = remember { SimpleDateFormat("EEE", Locale.getDefault()) }
    val dayFormatter = remember { SimpleDateFormat("dd", Locale.getDefault()) }

    val calendar = Calendar.getInstance()
    val daysOfWeek = (0..6).map {
        calendar.clone() as Calendar
    }.onEachIndexed { index, cal ->
        val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
        val delta = if (dayOfWeek == Calendar.SUNDAY) -6 else Calendar.MONDAY - dayOfWeek
        cal.add(Calendar.DAY_OF_YEAR, delta + index)
    }

    var selectedDate by remember { mutableStateOf(calendar.time) }
    LaunchedEffect(selectedDate) {
        classViewModel.fetchClassesInDate(FilterRequestBody(date = convertDateFormat(selectedDate.toString())))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        daysOfWeek.forEach { cal ->
            val isSelected = compareDatesIgnoringTime(cal.time, selectedDate)
            DayItem(
                day = dayFormatter.format(cal.time),
                dayOfWeek = dateFormatter.format(cal.time),
                isSelected = isSelected,
                onClick = { selectedDate = cal.time }
            )
        }
    }
}

fun compareDatesIgnoringTime(date1: Date, date2: Date): Boolean {
    val calendar1 = Calendar.getInstance().apply { time = date1 }
    val calendar2 = Calendar.getInstance().apply { time = date2 }

    return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
            calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
}

fun convertDateFormat(inputDate: String): String {
    // Định dạng của chuỗi ngày ban đầu
    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
    // Định dạng mong muốn
    val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    outputFormat.timeZone = TimeZone.getTimeZone("GMT")

    // Phân tích chuỗi ngày ban đầu thành đối tượng Date
    val date: Date = inputFormat.parse(inputDate)!!

    // Định dạng lại đối tượng Date thành chuỗi ngày với định dạng mong muốn
    return outputFormat.format(date)
}