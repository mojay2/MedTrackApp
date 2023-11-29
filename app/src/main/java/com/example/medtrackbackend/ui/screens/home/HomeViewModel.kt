package com.example.medtrackbackend.ui.screens.medicine_cabinet

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

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(
    private val repository: Repository = Graph.repository
): ViewModel(){
    var state by mutableStateOf(HomeState())
        private set

    init {
        getIntakeTimesToday()
//        getAllMedicine()
//        getAllPrograms()
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

    fun insertProgram(medicineId: Int,
                      programName:String,
                      startDate:Date,
                      weeks: Int,
                      numPills: Int,
                      interval: Int,
                      time: String){
        viewModelScope.launch {
            Log.d("Test Tag", time)
            repository.insertProgram(
                IntakeProgram(
                    medIdFk = medicineId,
                    programName=programName,
                    startDate =startDate,
                    weeks= weeks,
                    numPills= numPills,
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getIntakeTimesToday(){
//        private val dateUtil = DateUtil
//        val currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
        val currentDate = DateUtil().asDate(LocalDate.now())
        val currentDatetimeStamp = currentDate.time.toString()
        Log.d("Test Tag", currentDate.toString())
        Log.d("Test Tag", currentDatetimeStamp)
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

data class HomeState(
    val medicine: List<Medicine> = emptyList(),
    val medicinePrograms: List<IntakeProgram> = emptyList(),
    val intakeTimes: List<IntakeTimesWithProgramAndMedicine> = emptyList(),
    val intakeTimeChecked : Boolean = false,
    val programs: List<IntakeProgram> = emptyList()
)