package com.example.medtrackbackend.ui.composables

import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.time.LocalTime
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowTimePicker(
    context: Context,
    onTimeSelected: (LocalTime) -> Unit
): TimePickerDialog {
    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    val time = remember { mutableStateOf(LocalTime.now()) }
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            val selectedTime = LocalTime.of(hour, minute)
            time.value = selectedTime
            onTimeSelected.invoke(selectedTime)
        }, hour, minute, true
    )
    return timePickerDialog
}