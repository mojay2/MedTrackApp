package com.example.medtrackbackend.ui.screens.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import com.example.medtrackbackend.data.DateUtil
import com.example.medtrackbackend.data.IntakeTime
import com.example.medtrackbackend.data.Status
import com.example.medtrackbackend.data.TimeDao
import kotlinx.coroutines.isActive
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(
    private val repository: Repository = Graph.repository
): ViewModel(){
    var state by mutableStateOf(HomeState())
        private set

    init {
        getIntakeTimesToday()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getIntakeTimesToday(){
        val currentDate = DateUtil().asDate(LocalDate.now())
        viewModelScope.launch {
            repository.getAllTimesFromDate(currentDate).collectLatest {
                state = state.copy(
                    intakeTimes = it
                )
            }
        }
    }


    fun getIntakeTimesFromDate(date: Date){
        viewModelScope.launch {
            repository.getAllTimesFromDate(date).collectLatest {
                state = state.copy(
                    intakeTimes = it
                )
            }
        }
    }

    fun tookMedicine(items: IntakeTimesWithProgramAndMedicine){
        viewModelScope.launch {
            repository.updateIntakeTimeStatus(
                IntakeTime(
                    id = items.intakeTime.id,
                    programIdFk = items.intakeTime.programIdFk,
                    time = items.intakeTime.time,
                    intakeDate = items.intakeTime.intakeDate,
                    status = Status.TAKEN
                )
            )
            decrementMedicineQuantity(items)
        }
    }

    private fun decrementMedicineQuantity(items: IntakeTimesWithProgramAndMedicine){
        viewModelScope.launch {
            repository.updateMedicine(
                Medicine(
                    id = items.medicine.id,
                    medicineName = items.medicine.medicineName,
                    quantity = items.medicine.quantity - items.intakeProgram.numPills,
                    isActive = items.medicine.isActive,
                    dosage = items.medicine.dosage
                )
            )
        }
    }
}

data class HomeState(
    val medicine: List<Medicine> = emptyList(),
    val medicinePrograms: List<IntakeProgram> = emptyList(),
    val intakeTimes: List<IntakeTimesWithProgramAndMedicine> = emptyList(),
    val intakeTimeChecked : Boolean = false,
    val programs: List<IntakeProgram> = emptyList(),
    val dummyMedicine: Medicine = Medicine(999, "", 999,
        999.9, false),
    val activeMedicine: Medicine = Medicine(999, "", 999,
        999.9, false)
)