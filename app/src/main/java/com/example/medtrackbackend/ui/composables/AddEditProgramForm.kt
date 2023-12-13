package com.example.medtrackbackend.ui.composables

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.medtrackbackend.ui.util.AddEditMedicineFormData
import com.example.medtrackbackend.ui.util.AddEditProgramFormData
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditProgramForm(
    program: IntakeProgram,
    viewModel: AddEditProgramViewModel
){

    var isEditing = program.id != 999 && program.id != -1

    // TODO: Maybe change these? Medyo panget itsura nung variables HAHAH. ewan ko kung me form helper sa kotlin or compose
    var editedProgramName by remember { mutableStateOf(if(isEditing) program.programName else "") }
    var editedWeeks by remember { mutableStateOf(if(isEditing) program.weeks.toString() else "") }
    var editedDosageQuantity by remember { mutableStateOf(if(isEditing) program.numPills.toString() else "") }
    var editedStartDate by remember {
        mutableStateOf(DateUtil().asDate(LocalDate.now()))
    }
    val showDatePicker = remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val calendarState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    val timeState = rememberTimePickerState()
    val timeInputCount = remember { mutableIntStateOf(1) }
    val context = LocalContext.current

    var intakeTime by rememberSaveable {
        mutableStateOf(LocalTime.now(ZoneId.systemDefault()))
    }

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
                value = editedProgramName,
                onValueChange = { editedProgramName = it },
                label = { Text("Program Name") },
                placeholder = { Text("Enter program name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            DateToTextField(
                editedStartDate = editedStartDate,
                onClick = { showDatePicker.value = true }
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
            Spacer(modifier = Modifier.height(16.dp))

            val  timePickerDialog = ShowTimePicker(
                context = LocalContext.current,
                onTimeSelected = {
                    intakeTime = it
                }
            )

//            TODO: ADD MULTIPLE TIME INPUTS
            OutlinedTextField(
                value = TextFieldValue(intakeTime.format(DateTimeFormatter.ofPattern("HH:mm"))),
                onValueChange = {  },
                label = { Text("Intake Time") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        Icons.Outlined.AccessTime,
                        contentDescription = "Time Icon",
                        modifier = Modifier.clickable(onClick = { timePickerDialog.show() })
                    )
                },
                readOnly = true
            )
//            TimeItem(
//                timeInputCount = timeInputCount,
//                timeState = timeState,
//                onClickTime = { showTimePicker = true
//                }
//            )
        }
    }

    if (showDatePicker.value) {
        DateDialog(
            openDialog = showDatePicker,
            state = calendarState,
            onDateSelected = { selectedDate ->
                editedStartDate = DateUtil().asDate(selectedDate)
                Log.d("Test Program Form", editedStartDate.toString())
            }
        )
    }

    if (showTimePicker) {
        TimePickerDialog(
            onCancel = { showTimePicker = false },
            onConfirm = {
                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, timeState.hour)
                cal.set(Calendar.MINUTE, timeState.minute)
                cal.isLenient = false
                showTimePicker = false
                Toast.makeText(
                    context, "Entered time: ${cal.time}", Toast.LENGTH_SHORT
                ).show()
            },
        ) {
            TimeInput(state = timeState)
        }
    }

//    var formData = AddEditProgramFormData(
//        editedProgramName,
//        editedStartDate,
//        editedWeeks.toInt(),
//        editedDosageQuantity.toInt(),
//        intakeTime
//    )
//    viewModel.setFormData(formData)

//    return AddEditProgramFormData(
//        editedProgramName,
//        editedStartDate,
//        editedWeeks.toInt(),
//        editedDosageQuantity.toInt(),
//        intakeTime
//    )
}

