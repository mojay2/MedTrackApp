package com.example.medtrackbackend.ui.screens.medicine_cabinet

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.medtrackbackend.ui.composables.MedicineCard

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MedicineCabinetScreen(
    navController: NavController
){
    val medicineCabinetViewModel = viewModel(modelClass = MedicineCabinetViewModel::class.java)
    val medicineCabinetState = medicineCabinetViewModel.state

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.background(colorScheme.primary),
                contentColor = Color.Black
            ) {
                // Bottom app bar content
                Button(onClick = { navController.navigate("AddEditMedicine") }) {
                    Text("Create Medicine")
                }
                Button(onClick = { navController.navigate("Home") }) {
                    Text("Home")
                }
            }
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 56.dp), // Adjust this value based on your design
        ) {
            items(medicineCabinetState.medicine) { medicine ->
                MedicineCard(medicine = medicine, navController = navController)
            }
        }
    }
}