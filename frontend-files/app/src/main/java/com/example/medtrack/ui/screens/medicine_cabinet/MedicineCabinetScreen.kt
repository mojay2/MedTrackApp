package com.example.medtrack.ui.screens.medicine_cabinet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.medtrack.ui.composables.MainHeader
import com.example.medtrack.ui.composables.MedicationList
import com.example.medtrack.ui.theme.MedTrackTheme
import com.example.medtrack.ui.util.LocalCustomColorsPalette
import com.example.medtrack.ui.util.PageHeaderData
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
            MedicineCabinetFloatingActionButton(activeMedicine)
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
                        pageHeader = PageHeaderData.CABINET
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(top = 96.dp, start = 16.dp, end = 16.dp, bottom = 72.dp)
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

@Composable
fun MedicineCabinetFloatingActionButton(activeMedicine: Medication?) {
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
            text = { Text(text = if (activeMedicine == null) "Add Medicine" else "View Medicine") },
            icon = {
                Icon(
                    if (activeMedicine == null) Icons.Filled.Add else Icons.Outlined.StickyNote2,
                    null
                )
            },
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
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