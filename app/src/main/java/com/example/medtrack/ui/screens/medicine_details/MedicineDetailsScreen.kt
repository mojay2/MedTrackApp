package com.example.medtrack.ui.screens.medicine_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medtrack.data.model.IntakeProgram
import com.example.medtrack.data.model.Medication
import com.example.medtrack.data.util.sampleMedicationList
import com.example.medtrack.ui.composables.BottomNavBar
import com.example.medtrack.ui.composables.DetailsHeader
import com.example.medtrack.ui.composables.InfoCard
import com.example.medtrack.ui.composables.ProgramList
import com.example.medtrack.ui.theme.MedTrackTheme
import com.example.medtrack.ui.util.LocalCustomColorsPalette
import com.example.medtrack.ui.util.PageHeaderData
import com.example.medtrack.ui.util.Routes

@Composable
fun MedicineDetailsScreen(
    navController: NavHostController,
    medicine: Medication
) {
    var activeProgram by remember { mutableStateOf<IntakeProgram?>(null) }
    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = 32.dp)
                    .fillMaxWidth()
            ) {
                if (activeProgram == null) {
                    ExtendedFloatingActionButton(
                        containerColor = LocalCustomColorsPalette.current.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.primary,
                        text = { Text(text = "Add Program") },
                        icon = { Icon(Icons.Outlined.Add, "Add Icon") },
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    )
                } else {
                    ExtendedFloatingActionButton(
                        containerColor = LocalCustomColorsPalette.current.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.primary,
                        text = { Text(text = "Edit Program") },
                        icon = { Icon(Icons.Outlined.Today, "Date Icon") },
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    )
                }
                ExtendedFloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    text = { Text(text = "Edit Medicine") },
                    icon = { Icon(Icons.Outlined.Edit, "Edit Icon") },
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
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
                    DetailsHeader(
                        medication = medicine,
                        pageHeader = PageHeaderData.MEDICINE_DETAILS,
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(top = 136.dp, start = 16.dp, end = 16.dp, bottom = 112.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        InfoCard(
                            infoTitle = "Quantity",
                            infoText = "${medicine.quantity} pills left",
                            modifier = Modifier.weight(1f)
                        )
                        InfoCard(
                            infoTitle = "Pill Dosage",
                            infoText = "${medicine.dosage} mg",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ProgramList(
                        activeProgram,
                        onActiveProgramChange = { newActiveProgram ->
                            activeProgram = newActiveProgram
                        })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MedicineDetailsScreenPreview() {
    MedTrackTheme {
        val navController = rememberNavController()
        val sampleMedicine = sampleMedicationList[0]
        NavHost(
            navController = navController,
            startDestination = Routes.MEDICINE_DETAILS
        ) {
            composable(Routes.MEDICINE_DETAILS) {
                MedicineDetailsScreen(
                    navController = navController,
                    medicine = sampleMedicine
                )
            }
        }
    }
}