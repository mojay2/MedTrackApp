package com.example.medtrackbackend.ui.screens.medicine_details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.medtrackbackend.data.Medicine
import com.example.medtrackbackend.ui.composables.MedicineCard
import com.example.medtrackbackend.ui.composables.ProgramCard
import com.example.medtrackbackend.ui.screens.add_edit_medicine.MedicineDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MedicineDetailsScreen(
    navController: NavController,
    medicineIdString: String
){
    val medicineDetailsViewModel = viewModel(modelClass = MedicineDetailsViewModel::class.java)
    val medicineDetailsState = medicineDetailsViewModel.state

    //sets the selected medicine in the state
    medicineDetailsViewModel.getMedicine(Integer.parseInt(medicineIdString))
    //sets the programs of the selected medicine
    medicineDetailsViewModel.getIntakeProgramsForMedicine(medicineDetailsState.medicine)

    Log.d("test Tage","${medicineDetailsState.medicinePrograms}")


    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                contentColor = Color.Black
            ) {
                Button(onClick = { navController.navigate("MedicineCabinet") }) {
                    Text("Back")
                }
            }
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                "Medicine Details",
                modifier = Modifier.padding(6.dp)
            )
            Text(
                "Medicine Name: ${medicineDetailsState.medicine.medicineName}",
                modifier = Modifier.padding(6.dp)
            )
            Text(
                "Quantity: ${medicineDetailsState.medicine.quantity}",
                modifier = Modifier.padding(6.dp)
            )
            Text(
                "Dosage: ${medicineDetailsState.medicine.dosage}mg",
                modifier = Modifier.padding(6.dp)
            )
            Button(onClick = { navController.navigate("AddEditProgram/${medicineDetailsState.medicine.id}") }) {
                Text("Add Program")
            }
            if (medicineDetailsState.medicinePrograms.isEmpty()){
                Text("No Active Programs")
            }else{
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 56.dp), // Adjust this value based on your design
                ) {
                    items(medicineDetailsState.medicinePrograms) { program ->
                        ProgramCard(program = program)
                    }
                }
            }
        }
    }
}

