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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeItem(
    timeInputCount: MutableState<Int>,
    timeState: TimePickerState,
    onClickTime: () -> Unit,
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
    val timeInputs = List(timeInputCount.value) { _ ->
        val hour = timeState.hour.toString()
        val minute = timeState.minute.toString()

        OutlinedTextField(
            value = TextFieldValue("$hour:$minute"),
            readOnly = true,
            onValueChange = {},
            label = { Text("Time") },
            placeholder = { Text("Enter time") },
            trailingIcon = {
                Icon(
                    Icons.Outlined.AccessTime,
                    contentDescription = "Time Icon",
                    modifier = Modifier.clickable(onClick = onClickTime)
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