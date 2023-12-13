package com.example.medtrackbackend.ui.composables

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.medtrackbackend.data.DateUtil
import com.example.medtrackbackend.data.Medicine
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProgramCard(
    medicine:Medicine,
    programId: Int,
    onAddProgram: (
        medicineId: Int,
        programName:String,
        startDate: Date,
        weeks: Int,
        numPills: Int,
        time: LocalTime,
    ) -> Unit,
    onEditProgram: (
        medicineId: Int,
        programId: Int,
        programName:String,
        startDate: Date,
        weeks: Int,
        numPills: Int,
        time: LocalTime,
    ) -> Unit,
) {
    Log.d("Testing Tag", "Medicine ID Here in add program card: ${medicine.id}")
    val isEditing = programId != 999 && programId != -1
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            var medicineId = medicine.id
            var programName by rememberSaveable { mutableStateOf("") }
            var startDate by rememberSaveable { mutableStateOf(DateUtil().asDate(LocalDate.now())) }
            var weeks by rememberSaveable { mutableStateOf("") }
            var numPills by rememberSaveable { mutableStateOf("") }
            var intakeTime by rememberSaveable {
                mutableStateOf(LocalTime.now())
            }

            TextField(
                value = programName,
                onValueChange = { programName = it },
                label = { Text("Program Name") }
            )

            Row(verticalAlignment = Alignment.CenterVertically){
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = formatDate(startDate))
                Spacer(modifier = Modifier.size(4.dp))
                val mDatePicker = DatePickerDialog(
                    context = LocalContext.current,
                    onDateSelected = { localDate ->
                        startDate = DateUtil().asDate(localDate)
                    }
                )
                IconButton(onClick = { mDatePicker.show() }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Selected Time: ${intakeTime.format(DateTimeFormatter.ofPattern("HH:mm"))}")
                Spacer(modifier = Modifier.size(16.dp))
                val  timePickerDialog = ShowTimePicker(
                    context = LocalContext.current,
                    onTimeSelected = {
                        intakeTime = it
                    }
                )
                IconButton(onClick = { timePickerDialog.show() }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                }
            }
            TextField(
                value = weeks,
                onValueChange = {
                    // Ensure the input is a number
                    if (it.isDigitsOnly()) {
                        weeks = it
                    }
                },
                label = { Text("Number of Weeks") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            TextField(
                value = numPills,
                onValueChange = {
                    // Ensure the input is a number
                    if (it.isDigitsOnly()) {
                        numPills = it
                    }
                },
                label = { Text("Number of Pills") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            Button(
                onClick = {
                    //Validation
//                    val currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
                    val currentDate = DateUtil().asDate(LocalDate.now())
                    if ( programName.isNotBlank() &&
                        weeks.isNotBlank() && numPills.isNotBlank())
                    {
                        if(isEditing){
                            onEditProgram(medicineId.toInt(), programId, programName, startDate,
                                weeks.toInt(), numPills.toInt(), intakeTime
                            )
                        } else {
                            onAddProgram(medicineId.toInt(), programName, startDate,
                                weeks.toInt(), numPills.toInt(), intakeTime
                            )
                        }

                        programName = ""
                        weeks = ""
                        numPills = ""
                        startDate = DateUtil().asDate(LocalDate.now())
                        intakeTime = LocalTime.now()
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
            ) {
                if(isEditing) Text("Update Program") else Text("Add Program")
            }
        }
    }
}

fun formatDate(date: Date):String = SimpleDateFormat(
    "yyyy-MM-dd", Locale.getDefault())
    .format(date)

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toDate(): Date {
    return Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
}