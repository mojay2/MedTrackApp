package com.example.medtrackbackend.ui.screens.medicine_details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.medtrackbackend.data.IntakeProgram
import com.example.medtrackbackend.data.Medicine
import com.example.medtrackbackend.ui.composables.BottomNavBar
import com.example.medtrackbackend.ui.composables.MedicineCard
import com.example.medtrackbackend.ui.composables.MedicineDetailsHeader
import com.example.medtrackbackend.ui.composables.MedicineDetailsInfoRow
import com.example.medtrackbackend.ui.composables.ProgramCard
import com.example.medtrackbackend.ui.composables.ProgramList
import com.example.medtrackbackend.ui.screens.add_edit_medicine.MedicineDetailsViewModel
import com.example.medtrackbackend.ui.util.LocalCustomColorsPalette
import com.example.medtrackbackend.ui.util.PageHeaderData

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MedicineDetailsScreen(
    navController: NavController,
    medicineIdString: String
) {
    val medicineDetailsViewModel = viewModel(modelClass = MedicineDetailsViewModel::class.java)
    val medicineDetailsState = medicineDetailsViewModel.state

    //sets the selected medicine in the state
    medicineDetailsViewModel.getMedicine(Integer.parseInt(medicineIdString))
    //sets the programs of the selected medicine
    medicineDetailsViewModel.getIntakeProgramsForMedicine(medicineDetailsState.medicine)
    val medicine = medicineDetailsState.medicine

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        },
        floatingActionButton = {
            if(medicineDetailsState.medicinePrograms.isNotEmpty()){
                MedicineDetailsFloatingActionButton(
                    medicineDetailsState.medicinePrograms.first(),
                    navController,
                    medicineDetailsViewModel
                )
            } else {
                MedicineDetailsFloatingActionButton(
                    medicineDetailsState.dummyProgram,
                    navController,
                    medicineDetailsViewModel
                )
            }
        }
    ) { innerPadding ->
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
                    MedicineDetailsHeader(
                        medication = medicineDetailsState.medicine,
                        pageHeader = PageHeaderData.MEDICINE_DETAILS,
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(top = 136.dp, start = 16.dp, end = 16.dp, bottom = 112.dp)
                        .fillMaxWidth()
                ) {
                    MedicineDetailsInfoRow(medicineDetailsState.medicine)
                    Spacer(modifier = Modifier.height(16.dp))
                    // Check if medicineDetailsState.medicinePrograms is not null and not empty before using it
                    if (medicineDetailsState.medicinePrograms.isNotEmpty()) {
                        ProgramList(
                            medicineDetailsState.medicinePrograms.first(),
                            onActiveProgramChange = { newActiveProgram ->
                                // Handle active program change
                            },
                            medicineDetailsState.medicinePrograms
                        )
                    } else {
                        // You can display a loading state or a message when programs are empty
                        Text("No Programs found")
                    }
                }
            }
        }
    }
}

@Composable
fun MedicineDetailsFloatingActionButton(
    activeProgram: IntakeProgram,
    navController: NavController,
    viewModel: MedicineDetailsViewModel
) {
    var activeExists = activeProgram.id != 999

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 32.dp)
            .fillMaxWidth()
    ) {
        ExtendedFloatingActionButton(
            containerColor = LocalCustomColorsPalette.current.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.primary,
            text = { Text(text = if (activeExists) "Edit Program" else "Add Program") },
            icon = {
                Icon(
                    if (activeExists) Icons.Outlined.Today else Icons.Outlined.Add,
                    null
                )
            },
            onClick = { navController.navigate("AddEditProgram/${viewModel.state.medicine.id}")},
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )

        ExtendedFloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            text = { Text(text = "Edit Medicine") },
            icon = { Icon(Icons.Outlined.Edit, "Edit Icon") },
            onClick = { navController.navigate("AddEditMedicine/${viewModel.state.medicine.id}")},
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
    }
}