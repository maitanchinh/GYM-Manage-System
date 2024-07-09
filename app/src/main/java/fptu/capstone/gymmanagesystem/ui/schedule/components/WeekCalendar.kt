package fptu.capstone.gymmanagesystem.ui.schedule.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun WeekCalendar() {
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

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        daysOfWeek.forEach { cal ->
            val isSelected = selectedDate == cal.time
            DayItem(
                day = dayFormatter.format(cal.time),
                dayOfWeek = dateFormatter.format(cal.time),
                isSelected = isSelected,
                onClick = { selectedDate = cal.time }
            )
        }
    }
}