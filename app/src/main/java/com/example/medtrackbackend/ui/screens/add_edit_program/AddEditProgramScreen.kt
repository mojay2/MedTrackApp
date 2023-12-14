package com.example.medtrackbackend.ui.screens.add_edit_program

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.medtrackbackend.data.Medicine
import com.example.medtrackbackend.ui.composables.AddEditProgramForm
import com.example.medtrackbackend.ui.composables.BottomNavBar
import com.example.medtrackbackend.ui.composables.ConfirmModal
import com.example.medtrackbackend.ui.composables.FormDetailsHeader
import com.example.medtrackbackend.ui.composables.MedicineDetailsHeader
import com.example.medtrackbackend.ui.util.LocalCustomColorsPalette
import com.example.medtrackbackend.ui.util.PageHeaderData

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditProgramScreen(
    navController: NavController,
    medicineId: Int,
    programId: Int
){
    val addEditProgramViewModel = viewModel(modelClass = AddEditProgramViewModel::class.java)
    val addEditProgramState = addEditProgramViewModel.state
    val openDeleteDialog = remember { mutableStateOf(false) }
    val submitButtonText = if (programId != -1 && programId != 999)
        "Confirm Changes" else "Confirm Details"
    val pageHeader =
        if (programId != -1 && programId != 999) PageHeaderData.EDIT_PROGRAM else PageHeaderData.ADD_PROGRAM

    addEditProgramViewModel.getMedicine(medicineId)
    val medicine: Medicine =  addEditProgramState.medicine

    if (programId != -1) {
        addEditProgramViewModel.getEditingProgram(programId)
    //Todo: Fix these to remember inputs on edit mode
    //        addEditProgramViewModel.rememberEditingInputs()
    }
    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        },
        floatingActionButton = {
            AddEditProgramFloatingActionButton(
                submitButtonText,
                addEditProgramViewModel,
                navController,
                programId
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
                        pageHeader = pageHeader,
                        medication = addEditProgramState.dummyMedicine
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 72.dp)
                        .fillMaxWidth()
                ) {
                    FormDetailsHeader(
                        headerText = "Program Details",
                        subHeaderText = "Delete Program",
                        subHeaderOnClick = { openDeleteDialog.value = true },
                        showSubHeader = (programId != -1),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AddEditProgramForm(
                        program = addEditProgramState.editingProgram,
                        viewModel = addEditProgramViewModel
                    )
                }
            }
        }

        if (openDeleteDialog.value && (programId != -1)) {
            ConfirmModal(
                onDismissRequest = { openDeleteDialog.value = false },
                onConfirmation = {
                    addEditProgramViewModel.deleteProgram(programId)
                    navController.navigate("medicinecabinet")
                 },
                dialogTitle = "Are you sure you want to delete this Program?",
                dialogContent = {
                    Text(
                        text = "Medicine: ${addEditProgramState.medicine.medicineName}\n" +
                                "Program Name: ${addEditProgramState.editingProgram.programName}\n" +
                                "Date: ${addEditProgramState.editingProgram.startDate}\n" +
                                "Number of Weeks: ${addEditProgramState.editingProgram.weeks}\n" +
                                "Dosage: ${addEditProgramState.editingProgram.numPills}\n",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                icon = Icons.Filled.Check
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditProgramFloatingActionButton(
    submitButtonText: String,
    viewModel: AddEditProgramViewModel,
    navController: NavController,
    programId: Int
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 32.dp)
            .fillMaxWidth()
    ) {
        val toastSuccessText = if (programId != -1 && programId != 999)
            "Program was updated successfully" else "Program was inserted successfully"
        val toastInvalidText = if (programId != -1 && programId != 999)
            "Program was not updated" else "Program was not inserted"
        val context = LocalContext.current

        ExtendedFloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            text = { Text(text = submitButtonText) },
            icon = { Icon(Icons.Outlined.Check, "Check Icon") },
            onClick = {
                if (viewModel.validateInputs() && viewModel.insertStateInputs()) {
                    navController.navigate(
                        "MedicineDetails/${viewModel.state.medicine.id}")
                    Toast.makeText(context, toastSuccessText, Toast.LENGTH_SHORT).show()
                }else{
                    //Todo Add Prompt here for invalid inputs
                    Toast.makeText(context, toastInvalidText, Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
    }
}
