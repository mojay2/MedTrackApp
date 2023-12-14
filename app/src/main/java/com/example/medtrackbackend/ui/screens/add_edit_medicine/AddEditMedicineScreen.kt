package com.example.medtrackbackend.ui.screens.add_edit_medicine

import android.annotation.SuppressLint
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.medtrackbackend.data.Medicine
import com.example.medtrackbackend.ui.composables.AddEditMedicineForm
import com.example.medtrackbackend.ui.composables.BottomNavBar
import com.example.medtrackbackend.ui.composables.ConfirmModal
import com.example.medtrackbackend.ui.composables.FormDetailsHeader
import com.example.medtrackbackend.ui.composables.MedicineDetailsHeader
import com.example.medtrackbackend.ui.util.LocalCustomColorsPalette
import com.example.medtrackbackend.ui.util.PageHeaderData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditMedicineScreen(
    navController: NavController,
    medicineId: Int
) {
    val addEditMedicineViewModel = viewModel(modelClass = AddEditMedicineViewModel::class.java)
    val addEditMedicineState = addEditMedicineViewModel.state

    if (medicineId != -1) {
        addEditMedicineViewModel.getMedicine(medicineId)
    }
    val medicine: Medicine = addEditMedicineState.selectedMedicine
    val openDeleteDialog = remember { mutableStateOf(false) }
    val submitButtonText = if (medicine.id != 999) "Confirm Changes" else "Confirm Details"
    val pageHeader =
        if (medicine.id != 999) PageHeaderData.EDIT_MEDICINE_DETAILS
        else PageHeaderData.ADD_MEDICINE_DETAILS

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        },
        floatingActionButton = {
            AddEditMedicineFloatingActionButton(
                submitButtonText,
                addEditMedicineViewModel,
                medicine,
                navController,
                )
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
                        hideMedicineName = true,
                        navController = navController
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
                        showSubHeader = medicine.id != 999,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AddEditMedicineForm(
                        medicine = medicine,
                        addEditMedicineViewModel
                    )
                }
            }


            if (openDeleteDialog.value && medicine.id != 999) {
                ConfirmModal(
                    onDismissRequest = { openDeleteDialog.value = false },
                    onConfirmation = {
                        addEditMedicineViewModel.deleteMedicine(medicineId)
                        navController.navigate("medicinecabinet")
                    },
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
}

@Composable
fun AddEditMedicineFloatingActionButton(
    submitButtonText: String,
    viewModel: AddEditMedicineViewModel,
    medicine: Medicine,
    navController: NavController
) {
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
            onClick = {
                if (viewModel.validateInputs() && viewModel.insertStateInputs()) {
                    navController.navigate("medicinecabinet")
                } else {
                    // Todo: Add prompt for invalid inputs
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
    }
}
fun formatDate(date: Date):String = SimpleDateFormat(
    "yyyy-MM-dd", Locale.getDefault())
    .format(date)