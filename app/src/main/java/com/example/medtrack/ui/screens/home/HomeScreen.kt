package com.example.medtrack.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medtrack.R
import com.example.medtrack.ui.composables.BottomNavBar
import com.example.medtrack.ui.composables.HeaderPage
import com.example.medtrack.ui.composables.MedicationList
import com.example.medtrack.ui.theme.MedTrackTheme
import com.example.medtrack.ui.util.Routes

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                text = { Text(text = "Completed Dosage") },
                icon = { Icon(Icons.Filled.Check, "Check Icon") },
                onClick = { /*TODO*/ }
            )
        }
    ) { innerPadding ->
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column() {
                Box(
                    modifier = Modifier
                        .padding(0.dp)
                ) {
                    HeaderPage(paint = R.drawable.home)
                }
                Column(
                    modifier = Modifier
                        .padding(top = 56.dp, start = 16.dp, end = 16.dp)
                ) {
                    MedicationList()
                }
            }
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