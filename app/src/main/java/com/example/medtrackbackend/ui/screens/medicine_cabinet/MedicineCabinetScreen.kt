package com.example.medtrackbackend.ui.screens.medicine_cabinet

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.StickyNote2
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medtrackbackend.data.Medicine
import com.example.medtrackbackend.ui.composables.BottomNavBar
import com.example.medtrackbackend.ui.composables.MainHeader
import com.example.medtrackbackend.ui.composables.MedicationList
import com.example.medtrackbackend.ui.composables.MedicineCard
import com.example.medtrackbackend.ui.theme.MedTrackBackendTheme
import com.example.medtrackbackend.ui.util.LocalCustomColorsPalette
import com.example.medtrackbackend.ui.util.PageHeaderData
import com.example.medtrackbackend.ui.util.Routes

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
            BottomNavBar(navController)
        },
        floatingActionButton = {
            MedicineCabinetFloatingActionButton(
                medicineCabinetState.dummyMedicine,
                navController
            )
        }
    ) {innerPadding ->
        Surface(
            color = LocalCustomColorsPalette.current.surfaceContainer,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column() {
                Box(
                    modifier = Modifier
                        .padding(0.dp)
                ) {
                    MainHeader(
                        pageHeader = PageHeaderData.CABINET,
                        viewModel = null
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(top = 96.dp, start = 16.dp, end = 16.dp, bottom = 72.dp)
                ) {
                    MedicationList(
                        activeMedicine = medicineCabinetState.dummyMedicine,
                        onActiveMedicineChange = { newActiveMedicine ->
//                            activeMedicine = newActiveMedicine
                        },
                        isCabinet = true,
                        medications = medicineCabinetState.medicine,
                        navController
                    )
                }
            }
        }
    }
}

@Composable
fun MedicineCabinetFloatingActionButton(
    activeMedicine: Medicine,
    navController:NavController
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 32.dp)
            .fillMaxWidth()
    ) {
        ExtendedFloatingActionButton(
            containerColor = colorScheme.primary,
            contentColor = colorScheme.onPrimary,
            text = { Text(text = if (activeMedicine.id === 999) "Add Medicine" else "View Medicine") },
            icon = {
                Icon(
                    if (activeMedicine.id === 999) Icons.Filled.Add else Icons.Outlined.StickyNote2,
                    null
                )
            },
            onClick = {
                navController.navigate("AddEditMedicine/-1")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MedicineCabinetScreenPreview() {
    MedTrackBackendTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Routes.CABINET
        ) {
            composable(Routes.CABINET) {
                MedicineCabinetScreen(
                    navController = navController
                )
            }
        }
    }
}