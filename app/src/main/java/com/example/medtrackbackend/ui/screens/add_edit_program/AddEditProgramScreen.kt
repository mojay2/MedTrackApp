package com.example.medtrackbackend.ui.screens.add_edit_program

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.medtrackbackend.data.Medicine
import com.example.medtrackbackend.ui.composables.CreateProgramCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditProgramScreen(
    navController: NavController,
    medicineIdString: String
){
    val addEditProgramViewModel = viewModel(modelClass = AddEditProgramViewModel::class.java)
    val addEditProgramState = addEditProgramViewModel.state

    addEditProgramViewModel.getMedicine(Integer.parseInt(medicineIdString))
    addEditProgramViewModel.getLatestProgram()
    val medicine: Medicine =  addEditProgramState.medicine
    val latestProgramId: Int = addEditProgramState.latestProgram.id + 1

    Log.d("Testing Tag", "Medicine Id in add program screen: ${medicine.id}")
    Scaffold(
    ) {
        LazyColumn {
            item {
                CreateProgramCard(medicine,
                    onAddProgram = { medicineId, programName, startDate, weeks, numPills, time ->
                        addEditProgramViewModel.insertProgram(
                            medicineId, programName, startDate,
                            weeks, numPills, time
                        )
                    },
                    onAddTime = { time, startDate, weeks ->
                        addEditProgramViewModel.insertIntakeTimes(
                            latestProgramId ,startDate,weeks, time
                        )
                    }
                )
            }
            item{
                Button(onClick = { navController.navigate("MedicineDetails/${medicine.id}") }) {
                    Text("Back")
                }
            }
            }
        }
    }

fun formatDate(date: Date):String = SimpleDateFormat(
    "yyyy-MM-dd", Locale.getDefault())
    .format(date)
