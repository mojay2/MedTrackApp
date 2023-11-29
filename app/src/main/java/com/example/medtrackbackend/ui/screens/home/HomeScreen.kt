package com.example.medtrackbackend.ui.screens.medicine_cabinet

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.example.medtrackbackend.ui.composables.IntakeTimeCard
import com.example.medtrackbackend.ui.composables.MedicineCard

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController
){
    val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
    val homeState = homeViewModel.state

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.background(colorScheme.primary),
                contentColor = Color.Black
            ) {
                // Bottom app bar content
                Button(onClick = { navController.navigate("MedicineCabinet") }) {
                    Text("To Cabinet")
                }
            }
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 56.dp), // Adjust this value based on your design
        ) {
            if(homeState.intakeTimes.isEmpty()){
                item{
                    Text("EMPTY")
                }
            }
            items(homeState.intakeTimes) { intakeTime ->
//                MedicineCard(medicine = medicine, navController = navController)
                IntakeTimeCard(item = intakeTime)
            }
        }
    }
}