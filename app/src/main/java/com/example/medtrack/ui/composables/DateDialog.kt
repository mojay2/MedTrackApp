package com.example.medtrack.ui.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    openDialog: MutableState<Boolean>,
    state: DatePickerState,
    onDateSelected: (LocalDate) -> Unit
) {
    DatePickerDialog(
        onDismissRequest = { openDialog.value = false },
        confirmButton = {
            TextButton(
                onClick = {
                    openDialog.value = false
                    state.selectedDateMillis?.let { selectedDateMillis ->
                        val selectedDate = LocalDate.ofEpochDay(selectedDateMillis / 86400000)
                        onDateSelected(selectedDate)
                    }
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { openDialog.value = false }) {
                Text("CANCEL")
            }
        }
    ) {
        DatePicker(state = state)
    }
}