package com.example.medtrackbackend.ui.composables

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.medtrackbackend.ui.screens.add_edit_program.AddEditProgramViewModel
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeItems(
    timeInputCount: MutableState<Int>,
    viewModel: AddEditProgramViewModel
) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Time",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Add Time",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .clickable {
                        if (timeInputCount.value < 3) {
                            timeInputCount.value += 1
                        } else {
                            Toast.makeText(
                                context, "You can only add up to 3 times", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            )
            Text(
                text = "Remove Time",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .clickable {
                        if (timeInputCount.value > 1) {
                            timeInputCount.value -= 1
                            viewModel.onIntakeTimeChange(null, timeInputCount.value)
                        } else {
                            Toast.makeText(
                                context, "You must have at least 1 time", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    val timeInputs = List(timeInputCount.value) { index ->
        var selectedTime by remember { mutableStateOf<LocalTime?>(null) }
        val  timePickerDialog1 = ShowTimePicker(
            context = LocalContext.current,
            onTimeSelected = {
                Log.d("time input", "Time: ${it}, Index: $index")
                selectedTime = it
                viewModel.onIntakeTimeChange(it, index)
            }
        )
        val hour = selectedTime?.hour?.toString()?.padStart(2, '0') ?: ""
        val minute = selectedTime?.minute?.toString()?.padStart(2, '0') ?: ""

        OutlinedTextField(
            value = TextFieldValue("${hour}:${minute}"),
            readOnly = true,
            onValueChange = {},
            label = { Text("Time") },
            placeholder = { Text("Enter time") },
            trailingIcon = {
                Icon(
                    Icons.Outlined.AccessTime,
                    contentDescription = "Time Icon",
                    modifier = Modifier.clickable(onClick = {
                        timePickerDialog1.show()
                    })
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
    }

    timeInputs.forEach { timeInput ->
        timeInput
        Log.d("Test TimeItem", timeInput.toString())
    }
}