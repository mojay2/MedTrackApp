package com.example.medtrackbackend.ui.composables

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.medtrackbackend.data.DateUtil
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerDialog(
    context: Context,
    onDateSelected: (LocalDate) -> Unit
): DatePickerDialog {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val selectedDate = remember {
        mutableStateOf(DateUtil().asDate(LocalDate.now()))
    }

    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            val selectedDateOnly = LocalDate.of(mYear, mMonth + 1, mDayOfMonth)
            onDateSelected.invoke(selectedDateOnly)
        }, year, month, day
    )

    return mDatePickerDialog
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toDate(): Date {
    return Date.from(this.atZone(ZoneId.systemDefault()).toInstant())
}

@RequiresApi(Build.VERSION_CODES.O)
fun Date.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault())
}