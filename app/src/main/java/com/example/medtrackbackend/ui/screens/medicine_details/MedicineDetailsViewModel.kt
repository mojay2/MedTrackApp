package com.example.medtrackbackend.ui.screens.medicine_details

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
import java.util.Date

class MedicineDetailsViewModel(
    private val repository: Repository = Graph.repository
): ViewModel(){
    var state by mutableStateOf(MedicineDetailsState())
        private set

    init {
    }
    fun getMedicine(medicineId: Int){
        viewModelScope.launch {
            repository.getMedicineById(medicineId).collectLatest {
                state = state.copy(
                    medicine = it ?: Medicine(999,"",999, 999.9, false),
                )
            }
        }
    }

    fun getIntakeProgramsForMedicine(medicine: Medicine){
        viewModelScope.launch {
            repository.getProgramsFromMedicine(medicine.id).collectLatest {
                state = state.copy(
                    medicinePrograms = it
                )
            }
        }
    }

}

data class MedicineDetailsState(
    val medicine: Medicine = Medicine(999,"",999, 999.9, false),
    val medicinePrograms: List<IntakeProgram> = emptyList(),
    val intakeTimes: List<IntakeTimesWithProgramAndMedicine> = emptyList(),
    val intakeTimeChecked : Boolean = false,
    val dummyProgram: IntakeProgram = IntakeProgram(999, 999, "",
        Date(0), 999, 999)
)