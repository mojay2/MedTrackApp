package com.example.medtrackbackend.ui.composables

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.Button
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.medtrackbackend.data.DateUtil
import com.example.medtrackbackend.data.IntakeProgram
import com.example.medtrackbackend.ui.screens.add_edit_program.AddEditProgramViewModel
import java.time.format.DateTimeFormatter
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditProgramForm(
    viewModel: AddEditProgramViewModel
){
    // TODO: Maybe change these? Medyo panget itsura nung variables HAHAH. ewan ko kung me form helper sa kotlin or compose
    val showDatePicker = remember { mutableStateOf(false) }
    val calendarState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    val timeInputCount = remember { mutableIntStateOf(1) }
    val state = viewModel.state
    LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            item {
            OutlinedTextField(
                value = state.editedProgramName,
                onValueChange = {
                    viewModel.onNameChange(it)
                    Log.d("ProgramForm", it)
                },
                label = { Text("Program Name") },
                placeholder = { Text("Enter program name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            DateToTextField(
                editedStartDate = state.editedDate,
                onClick = { showDatePicker.value = true }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.editedWeeks,
                onValueChange = { viewModel.onWeekChange(it) },
                label = { Text("Number of Weeks") },
                placeholder = { Text("Enter number of weeks") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.editedNumPills,
                onValueChange = { viewModel.onNumPillsChange(it) },
                label = { Text("Dosage Quantity (pill)") },
                placeholder = { Text("Enter dosage quantity") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TimeItems(
                timeInputCount = timeInputCount,
                viewModel = viewModel
            )
        }
    }

    if (showDatePicker.value) {
        DateDialog(
            openDialog = showDatePicker,
            state = calendarState,
            onDateSelected = { selectedDate ->
                viewModel.onDateChange(DateUtil().asDate(selectedDate))
            }
        )
    }
}


