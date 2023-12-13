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
import com.example.medtrackbackend.data.IntakeProgram
import com.example.medtrackbackend.data.IntakeTime
import com.example.medtrackbackend.data.IntakeTimesWithProgramAndMedicine
import com.example.medtrackbackend.data.Medicine
import com.example.medtrackbackend.data.Status
import com.example.medtrackbackend.ui.composables.toLocalDateTime
import com.example.medtrackbackend.ui.repository.Repository
import com.example.medtrackbackend.ui.util.AddEditProgramFormData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate
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

//    fun getLatestProgram() {
//        viewModelScope.launch {
//            val latestProgram = repository.latestProgram.firstOrNull()
//            if (latestProgram != null) {
//                state = state.copy(latestProgram = latestProgram)
//            } else {
//                // Handle the error here
//            }
//        }
//    }
//
//    private fun getAllPrograms(){
//        viewModelScope.launch {
//            repository.allPrograms.collectLatest {
//                state = state.copy(
//                    programs = it
//                )
//            }
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertProgram(
        medicineId: Int,
        programName: String,
        startDate: Date,
        weeks: Int,
        numPills: Int,
        time: LocalTime,
    ) {
        viewModelScope.launch {
            try {
                // Insert the program
                val program = IntakeProgram(
                    medIdFk = medicineId,
                    programName = programName,
                    startDate = startDate,
                    weeks = weeks,
                    numPills = numPills
                )
                val createdProgramID = repository.insertProgram(program)
                state = state.copy(programId = createdProgramID.toInt())
                insertIntakeTimes(createdProgramID.toInt(), startDate, weeks, time)
                Log.d("AddEditProgramVM", "Program ID inserted:${createdProgramID}")
                Log.d("AddEditProgramVM", "Program ID in State:${state.programId}")
            } catch (e: Exception) {
                Log.e("AddEditProgramVM", "Insert Program failed: ${e.message}", e)
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
        time: LocalTime,
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
                deleteIntakeTimesFromProgramId(programId, startDate, weeks, time)
            } catch (e: Exception) {
                Log.e("AddEditProgramVM", "Update Program failed: ${e.message}", e)
            }
        }
    }

    fun deleteProgram(programId: Int){
        viewModelScope.launch{
            try{
                repository.deleteProgramFromId(programId)
//                state = state.copy(
//                    editingProgram = IntakeProgram(999,999,"", Date(0)
//                        ,999, 999)
//                )
                Log.e("AddEditProgramVM", "Deleted Program Successfully")
            } catch(e: Exception) {
                Log.e("AddEditProgramVM", "Delete Program failed: ${e.message}", e)
            }
        }
    }

    private fun deleteIntakeTimesFromProgramId(programId: Int, startDate: Date, weeks: Int, time: LocalTime){
        viewModelScope.launch {
            try {
                repository.deleteAllTimesFromProgramId(programId)
                insertIntakeTimes(programId, startDate, weeks, time)
            } catch (e: Exception) {
                Log.e("AddEditProgramVM", "Delete Program Times failed: ${e.message}", e)
            }
        }
    }

    private fun insertIntakeTimes(id: Int, startDate: Date, weeks: Int, time: LocalTime) {
        val intakeDates = generateIntakeDates(startDate, weeks)
        Log.d("AddEditProgramVM", "Program ID in Insert Time:${state.programId}")
        viewModelScope.launch {
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

    fun setFormData(formData: AddEditProgramFormData){
        state = state.copy(
            formData = formData
        )
    }

    fun resetFormData(){
        state = state.copy(
            formData = AddEditProgramFormData("", Date(0), 999
                , 999, LocalTime.now())
        )
    }
}

data class AddEditProgramState @RequiresApi(Build.VERSION_CODES.O) constructor(
    val medicine: Medicine = Medicine(999, "", 999,
        999.9, false),
    val medicinePrograms: List<IntakeProgram> = emptyList(),
    val intakeTimes: List<IntakeTimesWithProgramAndMedicine> = emptyList(),
    val intakeTimeChecked: Boolean = false,
    val programs: List<IntakeProgram> = emptyList(),
    val timeList: List<IntakeTime> = emptyList(),
    val latestProgram: IntakeProgram = IntakeProgram(-1, 999, "",
        Date(0),999, 999),
    val programId: Int = -1,
    val editingProgram: IntakeProgram = IntakeProgram(999,999,"", Date(0)
        ,999, 999),
    val dummyMedicine: Medicine = Medicine(999, "", 999,
        999.9, false),
    val formData: AddEditProgramFormData = AddEditProgramFormData("", Date(0), 999
        , 999, LocalTime.now())
)
