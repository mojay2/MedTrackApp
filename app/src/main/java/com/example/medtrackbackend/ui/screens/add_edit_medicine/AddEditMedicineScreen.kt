package com.example.medtrackbackend.ui.screens.add_edit_medicine

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.medtrackbackend.data.Medicine
import com.example.medtrackbackend.ui.composables.CreateMedicineCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditMedicineScreen(
    navController: NavController,
    medicineId: Int
){
    val addEditMedicineViewModel = viewModel(modelClass = AddEditMedicineViewModel::class.java)
    val addEditMedicineState = addEditMedicineViewModel.state

    if(medicineId != -1){
        addEditMedicineViewModel.getMedicine(medicineId)
    }
    val medicine: Medicine =  addEditMedicineState.selectedMedicine

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
        CreateMedicineCard(
            onAddMedicine = { medicineName, qty, dosage ->
                addEditMedicineViewModel.insertMedicine(medicineName, qty, dosage)
            },
            onEditMedicine = { medicine, medicineName, qty, dosage ->
                addEditMedicineViewModel.updateMedicine(medicineId, medicineName, qty, dosage)
            },
            medicine
        )
    }
}

fun formatDate(date: Date):String = SimpleDateFormat(
    "yyyy-MM-dd", Locale.getDefault())
    .format(date)
