package com.example.medtrack.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.medtrack.data.model.IntakeProgram

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditProgramForm(
    program: IntakeProgram? = null,
) {
    var editedProgramName by remember { mutableStateOf(program?.programName ?: "") }
    var editedStartDate by remember { mutableStateOf(program?.startDate ?: "") }
    var editedWeeks by remember { mutableStateOf(program?.weeks?.toString() ?: "") }
    var editedDosageQuantity by remember { mutableStateOf(program?.numPills.toString() ?: "") }

    val calendarState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    val openDialog = remember { mutableStateOf(true) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = editedProgramName,
            onValueChange = { editedProgramName = it },
            label = { Text("Program Name") },
            placeholder = { Text("Enter program name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        DatePicker(
            state = calendarState,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = editedWeeks,
            onValueChange = { editedWeeks = it },
            label = { Text("Number of Weeks") },
            placeholder = { Text("Enter number of weeks") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = editedDosageQuantity,
            onValueChange = { editedDosageQuantity = it },
            label = { Text("Dosage Quantity (pill)") },
            placeholder = { Text("Enter dosage quantity") },
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (openDialog.value) {
        DateDialog(
            openDialog = openDialog,
            state = calendarState,
            onDateSelected = { selectedDate ->
                editedStartDate = selectedDate
            }
        )
    }
}