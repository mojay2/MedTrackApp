package com.example.medtrackbackend.ui.composables

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.util.Calendar
import java.util.Date

@Composable
fun DatePickerDialog(
    context: Context,
    onDateSelected: (Date) -> Unit
): DatePickerDialog {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val selectedDate = remember {
        mutableStateOf("")
    }

    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear:Int, mMonth:Int, mDayOfMonth:Int ->
            val calendar = Calendar.getInstance()
            calendar.set(mYear, mMonth, mDayOfMonth)
            onDateSelected.invoke(calendar.time)
        }, year, month, day
    )
    return mDatePickerDialog
}