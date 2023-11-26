package com.example.medtrackbackend.ui.dashboard

import androidx.lifecycle.ViewModel
import com.example.medtrackbackend.ui.repository.Repository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.medtrackbackend.Graph
import com.example.medtrackbackend.data.IntakeProgram
import com.example.medtrackbackend.data.IntakeTimesWithProgramAndMedicine
import com.example.medtrackbackend.data.Medicine
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class DashboardViewModel(
    private val repository: Repository = Graph.repository
): ViewModel(){
    var state by mutableStateOf(DashboardState())
        private set

    init {
//        getIntakeTimesToday()
        getAllMedicine()
    }

    private fun getAllMedicine(){
        viewModelScope.launch {
            repository.allMedicine.collectLatest {
                state = state.copy(
                    medicine = it
                )
            }
        }
    }

     fun insertMedicine(medicineName: String, qty: Int, dosage:Double){
        viewModelScope.launch {
            repository.insertMedicine(
                Medicine(
                    medicineName = medicineName,
                    quantity = qty,
                    dosage = dosage,
                    isActive = true
                )
            )
        }
    }

    private fun getIntakeTimesToday(){
        val currentDate = Calendar.getInstance().time
        viewModelScope.launch {
            repository.getAllTimesFromDate(currentDate).collectLatest {
                state = state.copy(
                    intakeTimes = it
                )
            }
        }
    }

    private fun getIntakeTimesFromDate(date: Date){
        viewModelScope.launch {
            repository.getAllTimesFromDate(date).collectLatest {
                state = state.copy(
                    intakeTimes = it
                )
            }
        }
    }

    private fun intakeProgramsForMedicine(medicine: Medicine){
        viewModelScope.launch {
            repository.getProgramsFromMedicine(medicine.id).collectLatest {
                state = state.copy(
                    medicinePrograms = it
                )
            }
        }
    }

}

data class DashboardState(
    val medicine: List<Medicine> = emptyList(),
    val medicinePrograms: List<IntakeProgram> = emptyList(),
    val intakeTimes: List<IntakeTimesWithProgramAndMedicine> = emptyList(),
    val intakeTimeChecked : Boolean = false
)