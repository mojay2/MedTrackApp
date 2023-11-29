package com.example.medtrackbackend.ui.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medtrackbackend.data.IntakeTimesWithProgramAndMedicine
import com.example.medtrackbackend.data.Medicine
import com.example.medtrackbackend.data.Status
import com.example.medtrackbackend.ui.screens.medicine_cabinet.HomeViewModel
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun IntakeTimeCard(
    item: IntakeTimesWithProgramAndMedicine,
    homeViewModel: HomeViewModel,
    navController: NavController
) {
    var showDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { showDialog = true }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = item.intakeTime.id.toString())
            Text(text = item.medicine.medicineName)
            Text(text = item.intakeProgram.programName)
            Text(text = "${item.intakeProgram.numPills} pills")
            Text(text = "${item.intakeTime.intakeDate}")
            Text(text = item.intakeTime.time.format(DateTimeFormatter.ofPattern("HH:mm")))
                Checkbox(checked = (item.intakeTime.status == Status.TAKEN), onCheckedChange = {
                })
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirm Dosage?") },
                text = {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ){
                        Text("${item.medicine.medicineName}")
                        Text("${item.intakeProgram.numPills} pills")
                        Text(item.intakeTime.time.format(DateTimeFormatter.ofPattern("HH:mm")))
                    }
                },

                confirmButton = {
                    Button(onClick = {
                        homeViewModel.tookMedicine(item)
                        showDialog = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}