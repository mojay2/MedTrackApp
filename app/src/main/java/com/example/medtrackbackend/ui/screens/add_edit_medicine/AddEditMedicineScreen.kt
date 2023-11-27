package com.example.medtrackbackend.ui.screens.add_edit_medicine

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
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
import com.example.medtrackbackend.ui.composables.CreateMedicineCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditMedicineScreen(
    navController: NavController
){
    val addEditMedicineViewModel = viewModel(modelClass = AddEditMedicineViewModel::class.java)
    val addEditMedicineState = addEditMedicineViewModel.state

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
        CreateMedicineCard { medicineName, qty, dosage ->
            addEditMedicineViewModel.insertMedicine(medicineName, qty, dosage)
        }
    }
}

fun formatDate(date: Date):String = SimpleDateFormat(
    "yyyy-MM-dd", Locale.getDefault())
    .format(date)
