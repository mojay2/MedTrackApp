package com.example.medtrackbackend

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.medtrackbackend.ui.screens.add_edit_medicine.AddEditMedicineScreen
import com.example.medtrackbackend.ui.screens.add_edit_program.AddEditProgramScreen
import com.example.medtrackbackend.ui.screens.home.HomeScreen
import com.example.medtrackbackend.ui.screens.medicine_cabinet.MedicineCabinetScreen
import com.example.medtrackbackend.ui.screens.medicine_details.MedicineDetailsScreen
import com.example.medtrackbackend.ui.theme.MedTrackBackendTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavControllerProvider.initialize(navController)

            MedTrackBackendTheme {
                // A surface container using the 'background' color from the theme
                NavHost(navController = navController, startDestination = "Home") {
                    composable("MedicineCabinet") { MedicineCabinetScreen(navController) }
                    composable("Home") { HomeScreen(navController) }
                    composable("AddEditMedicine") { AddEditMedicineScreen(navController, -1) }
                    composable(
                        "AddEditMedicine/{medicineId}",
                        arguments = listOf(navArgument("medicineId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val medicineId = backStackEntry.arguments?.getString("medicineId") ?: ""
                        AddEditMedicineScreen(navController, medicineId.toInt())
                    }
                    composable(
                        "MedicineDetails/{medicineId}",
                        arguments = listOf(navArgument("medicineId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val medicineId = backStackEntry.arguments?.getString("medicineId") ?: ""
                        MedicineDetailsScreen(navController, medicineId)
                    }
                    composable(
                        "AddEditProgram/{medicineId}/{programId}",
                        arguments = listOf(
                            navArgument("medicineId") { type = NavType.IntType },
                            navArgument("programId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val medicineId = backStackEntry.arguments?.getInt("medicineId") ?: -1
                        val programId = backStackEntry.arguments?.getInt("programId") ?: -1
                        AddEditProgramScreen(navController, medicineId, programId)
                    }
                }
            }
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MedTrackBackendTheme {
        Greeting("Android")
    }
}