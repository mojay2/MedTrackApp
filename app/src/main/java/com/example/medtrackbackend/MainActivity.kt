package com.example.medtrackbackend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.medtrackbackend.ui.screens.medicine_cabinet.MedicineCabinetScreen
import com.example.medtrackbackend.ui.screens.medicine_details.MedicineDetailsScreen
import com.example.medtrackbackend.ui.theme.MedTrackBackendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            MedTrackBackendTheme {
                // A surface container using the 'background' color from the theme
                NavHost(navController = navController, startDestination = "MedicineCabinet") {
                    composable("MedicineCabinet") { MedicineCabinetScreen(navController) }
                    composable("AddEditMedicine") { AddEditMedicineScreen(navController) }
                    composable(
                        "MedicineDetails/{medicineId}",
                        arguments = listOf(navArgument("medicineId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val medicineId = backStackEntry.arguments?.getString("medicineId") ?: ""
                        MedicineDetailsScreen(navController, medicineId)
                    }
                    composable(
                        "AddEditProgram/{medicineId}",
                        arguments = listOf(navArgument("medicineId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val medicineId = backStackEntry.arguments?.getString("medicineId") ?: ""
                        AddEditProgramScreen(navController, medicineId)
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