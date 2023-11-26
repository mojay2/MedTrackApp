package com.example.medtrackbackend.ui.dashboard

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.annotation.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.medtrackbackend.data.IntakeProgram
import com.example.medtrackbackend.data.IntakeTimesWithProgramAndMedicine
import com.example.medtrackbackend.data.Medicine
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
){
    val dashboardViewModel = viewModel(modelClass = DashboardViewModel::class.java)
    val dashboardState = dashboardViewModel.state

    val openDialog = remember { mutableStateOf(false) }

    Scaffold(
//        bottomBar = {
//            BottomAppBar(
//                modifier = Modifier.background(MaterialTheme.colorScheme.primary),
//                contentColor = Color.Black
//            ) {
//                // Bottom app bar content
//                IconButton(onClick = {
//                    // Handle navigation icon click
//                }) {
//                    Icon(Icons.Default.ArrowBack, contentDescription = null)
//                }
//
//
//                // Spacer to push icons to the end
//                Spacer(Modifier.weight(1f))
//
//                IconButton(onClick = {
//                }) {
//                    Icon(Icons.Default.Add, contentDescription = null)
//                }
//            }
//        }
    ) {
        LazyColumn {
            item{
                CreateMedicineCard { medicineName, qty, dosage ->
                    dashboardViewModel.insertMedicine(medicineName, qty, dosage)
                }
            }
            item{
                CreateProgramCard{ medicineId,
                                   programName,
                                   startDate,
                                   weeks,
                                   numPills,
                                   interval, ->
                    dashboardViewModel.insertProgram(
                        medicineId, programName, startDate,
                        weeks, numPills, interval
                    )
                }
            }
            items(dashboardState.medicine) { medicine ->
                MedicineCard(medicine = medicine){
                    openDialog.value = true
                }
            }
            items(dashboardState.programs) { program ->
                ProgramCard(program = program)
            }
        }
    }
}
@Composable
fun MedicineDetailsCard(medicine: Medicine){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){

    }
}

@Composable
fun MedicineCard(medicine: Medicine, onCardClick: () -> Unit) {
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
            Text(text = "Medicine Name: ${medicine.medicineName}")
            Text(text = "Dosage: ${medicine.dosage} mg")
            Text(text = "${medicine.quantity} pills left. ")
        }
    }
}
@Composable
fun ProgramCard(program: IntakeProgram){
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ){
        Text(text = "Program Name: ${program.programName}")
        Text(text = "${program.weeks} weeks")
        Text(text = "${program.numPills} pills")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProgramCard(
    onAddProgram: (
        medicineId: Int,
        programName:String,
        startDate:Date,
        weeks: Int,
        numPills: Int,
        interval: Int,
    ) -> Unit,
) {
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
            var medicineId by rememberSaveable { mutableStateOf("") }
            var programName by rememberSaveable { mutableStateOf("") }
            var startDate by rememberSaveable { mutableStateOf(Calendar.getInstance().time) }
            var weeks by rememberSaveable { mutableStateOf("") }
            var numPills by rememberSaveable { mutableStateOf("") }
            var interval by rememberSaveable { mutableStateOf("") }

            Log.d("Test Tag", startDate.toString())
            TextField(
                value = medicineId,
                onValueChange = { medicineId = it },
                label = { Text("Medicine Id") }
            )

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
                val mDatePicker = datePickerDialog(
                    context = LocalContext.current,
                    onDateSelected = { date ->
                        startDate = date
                    }
                )
                IconButton(onClick = { mDatePicker.show() }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
                }
            }


            TextField(
                value = weeks,
                onValueChange = { weeks = it },
                label = { Text("Number of Weeks") }
            )

            TextField(
                value = numPills,
                onValueChange = { numPills = it },
                label = { Text("Number of Pills") }
            )

            TextField(
                value = interval,
                onValueChange = { interval = it },
                label = { Text("Interval") }
            )


            Button(
                onClick = {
                    // Perform validation and add the medicine to the database
                    if (medicineId.isNotBlank() && programName.isNotBlank() &&
                        weeks.isNotBlank() && numPills.isNotBlank() && interval.isNotBlank())
                    {

                        onAddProgram(medicineId.toInt(), programName, startDate,
                            weeks.toInt(), numPills.toInt(), interval.toInt()
                        )

                        // Clear the input fields after adding the medicine
                        medicineId = ""
                        programName = ""
                        weeks = ""
                        numPills = ""
                        interval = ""
                        startDate = Calendar.getInstance().time
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
            ) {
                Text("Add Program")
            }
        }
    }
}

@Composable
fun datePickerDialog(
    context: Context,
    onDateSelected: (Date) -> Unit
):DatePickerDialog{
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
        {_: DatePicker, mYear:Int, mMonth:Int, mDayOfMonth:Int ->
            val calendar = Calendar.getInstance()
            calendar.set(mYear, mMonth, mDayOfMonth)
            onDateSelected.invoke(calendar.time)
        }, year, month, day
    )
    return mDatePickerDialog
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMedicineCard(
    onAddMedicine: (medicineName: String, qty:Int, dosage: Double) -> Unit,
) {
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
            var medicineName by rememberSaveable { mutableStateOf("") }
            var qty by rememberSaveable { mutableStateOf("") }
            var dosage by rememberSaveable { mutableStateOf("") }

            TextField(
                value = medicineName,
                onValueChange = { medicineName = it },
                label = { Text("Medicine Name") }
            )

            TextField(
                value = qty,
                onValueChange = { qty = it },
                label = { Text("Quantity") }
            )

            TextField(
                value = dosage,
                onValueChange = { dosage = it },
                label = { Text("Dosage") }
            )

            Button(
                onClick = {
                    // Perform validation and add the medicine to the database
                    if (medicineName.isNotBlank() && dosage.isNotBlank() && qty.isNotBlank()) {
                        // Convert dosage to Double
                        val dosageValue = dosage.toDouble()
                        val qtyValue = qty.toInt()
                        // Call the provided callback to add the medicine to the database
                        onAddMedicine(medicineName, qtyValue,dosageValue)

                        // Clear the input fields after adding the medicine
                        medicineName = ""
                        qty = ""
                        dosage = ""
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
            ) {
                Text("Add Medicine")
            }
        }
    }
}

fun formatDate(date: Date):String = SimpleDateFormat(
    "yyyy-MM-dd", Locale.getDefault())
    .format(date)

