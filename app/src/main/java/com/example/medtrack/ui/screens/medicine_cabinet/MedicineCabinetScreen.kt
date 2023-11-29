package com.example.medtrack.ui.screens.medicine_cabinet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.StickyNote2
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medtrack.data.model.Medication
import com.example.medtrack.ui.composables.BottomNavBar
import com.example.medtrack.ui.composables.CabinetHeader
import com.example.medtrack.ui.composables.MedicationList
import com.example.medtrack.ui.theme.MedTrackTheme
import com.example.medtrack.ui.util.LocalCustomColorsPalette
import com.example.medtrack.ui.util.Routes

@Composable
fun MedicineCabinetScreen(
    navController: NavHostController
) {
    var activeMedicine by remember { mutableStateOf<Medication?>(null) }
    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        },
        floatingActionButton = {
            if (activeMedicine == null) {
                ExtendedFloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    text = { Text(text = "Add Medicine") },
                    icon = { Icon(Icons.Filled.Add, "Add Icon") },
                    onClick = { }
                )
            } else {
                ExtendedFloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    text = { Text(text = "View Medicine") },
                    icon = { Icon(Icons.Outlined.StickyNote2, "Note Icon") },
                    onClick = { }
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
                    CabinetHeader()
                }
                Column(
                    modifier = Modifier
                        .padding(top = 96.dp, start = 16.dp, end = 16.dp)
                ) {
                    MedicationList(
                        activeMedicine,
                        onActiveMedicineChange = { newActiveMedicine ->
                            activeMedicine = newActiveMedicine
                        },
                        isCabinet = true
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MedicineCabinetScreenPreview() {
    MedTrackTheme {
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