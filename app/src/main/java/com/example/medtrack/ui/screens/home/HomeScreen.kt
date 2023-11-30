package com.example.medtrack.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.example.medtrack.data.model.Medication
import com.example.medtrack.ui.composables.BottomNavBar
import com.example.medtrack.ui.composables.ConfirmModal
import com.example.medtrack.ui.composables.MainHeader
import com.example.medtrack.ui.composables.MedicationList
import com.example.medtrack.ui.theme.MedTrackTheme
import com.example.medtrack.ui.util.LocalCustomColorsPalette
import com.example.medtrack.ui.util.PageHeaderData
import com.example.medtrack.ui.util.Routes

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    var activeMedicine by remember { mutableStateOf<Medication?>(null) }
    val openConfirmDialog = remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        },
        floatingActionButton = {
            HomeFloatingActionButton(
                activeMedicine = activeMedicine,
                openConfirmDialog = openConfirmDialog
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
                    MainHeader(
                        pageHeader = PageHeaderData.HOME
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(top = 56.dp, start = 16.dp, end = 16.dp, bottom = 72.dp)
                ) {
                    MedicationList(
                        activeMedicine,
                        onActiveMedicineChange = { newActiveMedicine ->
                            activeMedicine = newActiveMedicine
                        })

                }
            }

            if (openConfirmDialog.value && activeMedicine != null) {


                ConfirmModal(
                    onDismissRequest = { openConfirmDialog.value = false },
                    onConfirmation = { openConfirmDialog.value = false },
                    dialogTitle = "Confirm Dosage",
                    dialogContent = {
                        Text(
                            text = "Medicine: ${activeMedicine?.medicineName}\n" +
                                    "Dosage: 1 pill\n" +
                                    "Time: 6:00 am",
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
private fun HomeFloatingActionButton(
    activeMedicine: Medication?,
    openConfirmDialog: MutableState<Boolean>
) {
    if (activeMedicine != null) {
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
                text = { Text(text = "Completed Dosage") },
                icon = { Icon(Icons.Filled.Check, "Check Icon") },
                onClick = { openConfirmDialog.value = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MedTrackTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Routes.HOME
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    navController = navController
                )
            }
        }
    }
}