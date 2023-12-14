package com.example.medtrackbackend.ui.screens.medicine_cabinet

import android.util.Log
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

class MedicineCabinetViewModel(
    private val repository: Repository = Graph.repository
): ViewModel(){
    var state by mutableStateOf(MedicineCabinetState())
        private set

    init {
        getAllMedicine()
        getAllPrograms()
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

    private fun getAllPrograms(){
        viewModelScope.launch {
            repository.allPrograms.collectLatest {
                state = state.copy(
                    programs = it
                )
            }
        }
    }

    fun setActiveMedicine(medicine: Medicine){
        state = state.copy(
            activeMedicine = medicine
        )
    }
}

data class MedicineCabinetState(
    val medicine: List<Medicine> = emptyList(),
    val programs: List<IntakeProgram> = emptyList(),
    val dummyMedicine: Medicine = Medicine(999, "", 999,
        999.9, false),
    val activeMedicine: Medicine = Medicine(999, "", 999,
        999.9, false)
)