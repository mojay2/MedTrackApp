package com.example.medtrackbackend.ui.screens.add_edit_program

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medtrackbackend.Graph
import com.example.medtrackbackend.data.DateUtil
import com.example.medtrackbackend.data.IntakeProgram
import com.example.medtrackbackend.data.IntakeTime
import com.example.medtrackbackend.data.Medicine
import com.example.medtrackbackend.data.Status
import com.example.medtrackbackend.ui.repository.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
class AddEditProgramViewModel(
    private val repository: Repository = Graph.repository
) : ViewModel() {
    var state by mutableStateOf(AddEditProgramState())
        private set

    fun getMedicine(medicineId: Int) {
        viewModelScope.launch {
            repository.getMedicineById(medicineId).collectLatest {
                state = state.copy(
                    medicine = it
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertProgram(
        medicineId: Int,
        programName: String,
        startDate: Date,
        weeks: Int,
        numPills: Int,
        times: List<LocalTime?>,
    ) {
        viewModelScope.launch {
            try {
                val program = IntakeProgram(
                    medIdFk = medicineId,
                    programName = programName,
                    startDate = startDate,
                    weeks = weeks,
                    numPills = numPills
                )
                val createdProgramID = repository.insertProgram(program)
                state = state.copy(programId = createdProgramID.toInt())
                insertIntakeTimes(createdProgramID.toInt(), startDate, weeks, times)
                updateMedicineActiveStatus(medicineId, isActive = true)
                Log.d("AddEditProgramVM", "Program ID inserted:${createdProgramID}")
                Log.d("AddEditProgramVM", "Program ID in State:${state.programId}")
            } catch (e: Exception) {
                Log.e("AddEditProgramVM", "Insert Program failed: ${e.message}", e)
            }
        }
    }

    private fun updateMedicineActiveStatus(id: Int, isActive: Boolean){
        viewModelScope.launch {
            try {
                repository.updateMedicineStatus(id, isActive)
            } catch (e: Exception){
                Log.e("AddEditProgramVM", "Update Med Status Failed: ${e.message}", e)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateProgram(
        medicineId: Int,
        programId: Int,
        programName: String,
        startDate: Date,
        weeks: Int,
        numPills: Int,
        times: List<LocalTime?>,
    ) {
        viewModelScope.launch {
            try {
                // Insert the program
                val program = IntakeProgram(
                    id = programId,
                    medIdFk = medicineId,
                    programName = programName,
                    startDate = startDate,
                    weeks = weeks,
                    numPills = numPills
                )
                repository.updateProgram(program)
                deleteIntakeTimesFromProgramId(programId, startDate, weeks, times)
            } catch (e: Exception) {
                Log.e("AddEditProgramVM", "Update Program failed: ${e.message}", e)
            }
        }
    }

    fun deleteProgram(programId: Int){
        viewModelScope.launch{
            try{
                repository.deleteProgramFromId(programId)
                Log.e("AddEditProgramVM", "Deleted Program Successfully")

                getIntakeProgramsForMedicine(state.medicine)
                if(state.programList.isEmpty()){
                    updateMedicineActiveStatus(state.medicine.id, isActive = false)
                }
            } catch(e: Exception) {
                Log.e("AddEditProgramVM", "Delete Program failed: ${e.message}", e)
            }
        }
    }

    private suspend fun getIntakeProgramsForMedicine(medicine: Medicine) {
        val programs = repository.getProgramsFromMedicine(medicine.id).first()
        state = state.copy(programList = programs)
    }

    private fun deleteIntakeTimesFromProgramId(programId: Int, startDate: Date, weeks: Int, times: List<LocalTime?>){
        viewModelScope.launch {
            try {
                repository.deleteAllTimesFromProgramId(programId)
                insertIntakeTimes(programId, startDate, weeks, times)
            } catch (e: Exception) {
                Log.e("AddEditProgramVM", "Delete Program Times failed: ${e.message}", e)
            }
        }
    }

    private fun insertIntakeTimes(id: Int, startDate: Date, weeks: Int, times: List<LocalTime?>) {
        val intakeDates = generateIntakeDates(startDate, weeks)
        Log.d("AddEditProgramVM", "Program ID in Insert Time:${state.programId}")
        viewModelScope.launch {
            times.forEach{ time ->
                if(time != null){
                    intakeDates.forEach { intakeDate ->
                        try {
                            repository.insertTime(
                                IntakeTime(
                                    programIdFk = id,
                                    time = time,
                                    intakeDate = intakeDate,
                                    status = Status.UPCOMING
                                )
                            )
                            Log.e("AddEditProgramVM", "Time Inserted:${time}")
                        } catch (e: Exception) {
                            Log.e("AddEditProgramVM", "Insert Intake Time failed: ${e.message}", e)
                        }
                    }
                }
            }
        }
    }

    private fun generateIntakeDates(startDate: Date, weeks: Int): List<Date> {
        val calendar = Calendar.getInstance()
        calendar.time = startDate

        val intakeDates = mutableListOf<Date>()
        repeat(weeks * 7) {
            intakeDates.add(calendar.time)
            calendar.add(Calendar.DAY_OF_YEAR, 1) // Increment by one day for the next iteration
        }

        return intakeDates
    }

    fun getEditingProgram(programId: Int) {
        viewModelScope.launch {
            repository.getProgramById(programId).collectLatest {
                state = state.copy(
                    editingProgram = it ?: IntakeProgram(999, 999, "",
                        Date(0), 999, 999)
                )
            }
        }
    }

    fun onNameChange(newValue:String){
        Log.d("AddEditProgramVM", "onNameChange called with $newValue")
        state = state.copy(editedProgramName = newValue)
    }

    fun onDateChange(newValue:Date){
        Log.d("AddEditProgramVM", "onDateChange called with $newValue")
        state = state.copy(editedDate = newValue)
    }

    fun onWeekChange(newValue:String){
        Log.d("AddEditProgramVM", "onWeekChange called with $newValue")
        try {
            state = state.copy(editedWeeks = newValue)
        } catch (e: NumberFormatException) {
            Log.e("AddEditProgramVM", "Error Updating Weeks: ${e.message}", e)
        }
    }

    fun onNumPillsChange(newValue:String){
        Log.d("AddEditProgramVM", "onNumPillsChanged called with $newValue")
        try {
            state = state.copy(editedNumPills = newValue)
        } catch (e: NumberFormatException) {
            Log.e("AddEditProgramVM", "Error Updating Num Pills: ${e.message}", e)
        }
    }

    fun onTimeChange(newValue:LocalTime){
        Log.d("AddEditProgramVM", "onTimeChange called with $newValue")
        state = state.copy(editedTime = newValue)
    }

    fun validateInputs():Boolean {
        val weeksAsInt = state.editedWeeks.toIntOrNull()
        val numPillsAsInt = state.editedNumPills.toIntOrNull()

        if(state.editedProgramName == "" || state.editedDate == Date(0) ||
            state.editedWeeks == "" || state.editedNumPills == "" ||
            weeksAsInt == null || numPillsAsInt == null){
            return false
        }
        return true
    }

    fun insertStateInputs():Boolean{
        var intakeTimes = listOf(state.intakeTime0, state.intakeTime1, state.intakeTime2)
        return try {
            if(state.editingProgram.id == 999){
                insertProgram(
                    state.medicine.id,
                    state.editedProgramName,
                    state.editedDate,
                    state.editedWeeks.toInt(),
                    state.editedNumPills.toInt(),
                    intakeTimes
                )
            } else {
                updateProgram(
                    state.medicine.id,
                    state.editingProgram.id,
                    state.editedProgramName,
                    state.editedDate,
                    state.editedWeeks.toInt(),
                    state.editedNumPills.toInt(),
                    intakeTimes
                )
            }
            true
        } catch(e: Exception) {
            Log.d("AddEditProgramVM", "Error Calling Insert Function")
            false
        }
    }

    fun onIntakeTimeChange(time: LocalTime?, index: Int){
        state = when (index) {
            0 -> {
                state.copy(
                    intakeTime0 = time
                )
            }
            1 -> {
                state.copy(
                    intakeTime1 = time
                )
            }
            else -> {
                state.copy(
                    intakeTime2 = time
                )
            }
        }
    }

    fun rememberEditingInputs(){  //Broken. will fix
        state = state.copy(
            editedProgramName = state.editingProgram.programName,
            editedDate = state.editingProgram.startDate,
            editedWeeks = state.editingProgram.weeks.toString(),
            editedNumPills = state.editingProgram.numPills.toString()
        )
    }

}

data class AddEditProgramState @RequiresApi(Build.VERSION_CODES.O) constructor(
    val medicine: Medicine = Medicine(999, "", 999,
        999.9, false),
    val programId: Int = -1,
    val editingProgram: IntakeProgram = IntakeProgram(999,999,"", Date(0)
        ,999, 999),
    val dummyMedicine: Medicine = Medicine(999, "", 999,
        999.9, false),
    val editedProgramName: String = "",
    val editedDate: Date = DateUtil().resetTimeToMidnight(Date()),
    val editedWeeks: String = "",
    val editedNumPills: String = "",
    val editedTime: LocalTime = LocalTime.now().withSecond(0),
    val programList: List<IntakeProgram> = emptyList(),
    val intakeTime0: LocalTime? = null,
    val intakeTime1: LocalTime? = null,
    val intakeTime2: LocalTime? = null,
)
