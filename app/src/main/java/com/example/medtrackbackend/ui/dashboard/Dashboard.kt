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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medtrackbackend.data.IntakeTimesWithProgramAndMedicine
import com.example.medtrackbackend.data.Medicine
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
){
    val dashboardViewModel = viewModel(modelClass = DashboardViewModel::class.java)
    val dashboardState = dashboardViewModel.state

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                contentColor = Color.Black
            ) {
                // Bottom app bar content
                IconButton(onClick = {
                    // Handle navigation icon click
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }


                // Spacer to push icons to the end
                Spacer(Modifier.weight(1f))

                IconButton(onClick = {
                }) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        }
    ) {
        LazyColumn {
            item{
                CreateMedicineCard { medicineName, qty, dosage ->
                    dashboardViewModel.insertMedicine(medicineName, qty, dosage)
                }
            }
            items(dashboardState.medicine) { medicine ->
                MedicineCard(medicine = medicine)
            }
        }
    }
}


@Composable
fun MedicineCard(medicine: Medicine) {
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
            Text(text = "Medicine Amount: ${medicine.dosage}")
        }
    }
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

@Preview
@Composable
fun MedicineCardPreview() {
    MedicineCard(medicine = Medicine(45, "Name", 35, 45.0, true))
}