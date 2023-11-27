package com.example.medtrackbackend.ui.composables

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.util.Calendar

@Composable
fun ShowTimePicker(
    context: Context,
    onTimeSelected: (String) -> Unit
): TimePickerDialog {
    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    val time = remember { mutableStateOf("") }
    val timePickerDialog = TimePickerDialog(
        context,
        {_, hour : Int, minute: Int ->
            time.value = "$hour:$minute"
            onTimeSelected.invoke(time.value)
        }, hour, minute, true
    )
    return timePickerDialog
}