package com.example.medtrack.ui.screens.add_edit_program

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
import com.example.medtrack.data.model.IntakeProgram
import com.example.medtrack.data.util.sampleProgramList
import com.example.medtrack.ui.composables.AddEditProgramForm
import com.example.medtrack.ui.composables.BottomNavBar
import com.example.medtrack.ui.composables.ConfirmModal
import com.example.medtrack.ui.composables.FormDetailsHeader
import com.example.medtrack.ui.composables.MedicineDetailsHeader
import com.example.medtrack.ui.theme.MedTrackTheme
import com.example.medtrack.ui.util.LocalCustomColorsPalette
import com.example.medtrack.ui.util.PageHeaderData
import com.example.medtrack.ui.util.Routes

@Composable
fun AddEditProgramScreen(
    navController: NavHostController,
    program: IntakeProgram? = null
) {
    val openDeleteDialog = remember { mutableStateOf(false) }

    val submitButtonText = if (program != null) "Confirm Changes" else "Confirm Details"
    val pageHeader =
        if (program != null) PageHeaderData.EDIT_PROGRAM else PageHeaderData.ADD_PROGRAM

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        },
        floatingActionButton = {
            AddEditProgramFloatingActionButton(submitButtonText)
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
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 72.dp)
                        .fillMaxWidth()
                ) {
                    FormDetailsHeader(
                        program = program,
                        openDeleteDialog = openDeleteDialog,
                        headerText = "Program Details",
                        sideText = "Delete Program"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AddEditProgramForm(
                        program = program
                    )
                }
            }
        }

        if (openDeleteDialog.value && program != null) {
            ConfirmModal(
                onDismissRequest = { openDeleteDialog.value = false },
                onConfirmation = { openDeleteDialog.value = false },
                dialogTitle = "Are you sure you want to delete this medicine?",
                dialogContent = {
                    Text(
                        text = "Medicine: Paractemol\n" +
                                "Program Name: Flu\n" +
                                "Date: Nov 15, 2023 - Jan 10, 2024\n" +
                                "Number of Weeks: 8\n" +
                                "Dosage: 1 pill\n" +
                                "Time: 6:00 am, 12:00 pm, 8:00p\n",
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
fun AddEditProgramFloatingActionButton(submitButtonText: String) {
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
fun AddEditProgramScreen() {
    MedTrackTheme {
        val navController = rememberNavController()
        val sampleProgram = sampleProgramList[0]
        NavHost(
            navController = navController,
            startDestination = Routes.ADD_EDIT_PROGRAM
        ) {
            composable(Routes.ADD_EDIT_PROGRAM) {
                AddEditProgramScreen(
                    navController = navController,
//                    program = sampleProgram
                )
            }
        }
    }
}