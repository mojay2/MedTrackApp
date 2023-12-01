package com.example.medtrack.ui.screens.add_edit_medicine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medtrack.data.model.Medication
import com.example.medtrack.data.util.sampleMedicationList
import com.example.medtrack.ui.composables.AddEditMedicineForm
import com.example.medtrack.ui.composables.BottomNavBar
import com.example.medtrack.ui.composables.ConfirmModal
import com.example.medtrack.ui.composables.FormDetailsHeader
import com.example.medtrack.ui.composables.MedicineDetailsHeader
import com.example.medtrack.ui.theme.MedTrackTheme
import com.example.medtrack.ui.util.LocalCustomColorsPalette
import com.example.medtrack.ui.util.PageHeaderData
import com.example.medtrack.ui.util.Routes

@Composable
fun AddEditMedicineScreen(
    navController: NavHostController,
    medicine: Medication? = null
) {
    val openDeleteDialog = remember { mutableStateOf(false) }

    val submitButtonText = if (medicine != null) "Confirm Changes" else "Confirm Details"
    val pageHeader =
        if (medicine != null) PageHeaderData.EDIT_MEDICINE_DETAILS else PageHeaderData.ADD_MEDICINE_DETAILS

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        },
        floatingActionButton = {
            AddEditMedicineFloatingActionButton(submitButtonText)
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
                        medication = medicine,
                        pageHeader = pageHeader,
                        hideMedicineName = true
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 72.dp)
                        .fillMaxWidth()
                ) {
                    FormDetailsHeader(
                        headerText = "Medicine Details",
                        subHeaderText = "Delete Medicine",
                        subHeaderOnClick = { openDeleteDialog.value = true },
                        showSubHeader = medicine != null,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AddEditMedicineForm(
                        medicine = medicine
                    )
                }
            }
        }

        if (openDeleteDialog.value && medicine != null) {
            ConfirmModal(
                onDismissRequest = { openDeleteDialog.value = false },
                onConfirmation = { openDeleteDialog.value = false },
                dialogTitle = "Are you sure you want to delete this medicine?",
                dialogContent = {
                    Text(
                        text = "Medicine: ${medicine.medicineName}\n" +
                                "Quantity: ${medicine.quantity} pills\n" +
                                "Pill Dosage: ${medicine.dosage} mg\n" +
                                "Number of Programs: 4",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                icon = Icons.Filled.Check
            )
        }
    }
}

@Composable
fun AddEditMedicineFloatingActionButton(submitButtonText: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 32.dp)
            .fillMaxWidth()
    ) {
        ExtendedFloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            text = { Text(text = submitButtonText) },
            icon = { Icon(Icons.Outlined.Check, "Check Icon") },
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditMedicineScreen() {
    MedTrackTheme {
        val navController = rememberNavController()
        val sampleMedicine = sampleMedicationList[0]
        NavHost(
            navController = navController,
            startDestination = Routes.ADD_EDIT_MEDICINE_DETAILS
        ) {
            composable(Routes.ADD_EDIT_MEDICINE_DETAILS) {
                AddEditMedicineScreen(
                    navController = navController,
//                    medicine = sampleMedicine
                )
            }
        }
    }
}