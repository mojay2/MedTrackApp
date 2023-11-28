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
import com.example.medtrackbackend.ui.repository.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import java.util.Date

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

    fun getLatestProgram() {
        viewModelScope.launch {
            repository.latestProgram.collectLatest {
                state = state.copy(
                    latestProgram = it
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

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun insertProgram(
//        medicineId: Int,
//        programName: String,
//        startDate: Date,
//        weeks: Int,
//        numPills: Int,
//        time: LocalTime
//    ) {
//        viewModelScope.launch {
//            // Insert the program
//            repository.insertProgram(
//                IntakeProgram(
//                    medIdFk = medicineId,
//                    programName = programName,
//                    startDate = startDate,
//                    weeks = weeks,
//                    numPills = numPills,
//                )
//            )
//
//            Log.d("Test Tag" , "Program Inserted")
//            val programFk = countTimeEntries()
//            Log.d("Test Tag" , programFk.toString())
////            // Generate intake times
//            val intakeTimes = generateIntakeTimes(startDate, weeks, time)
////            Log.d("Test Tag" , intakeTimes.toString())
//
////
////            // Insert intake times into the database
//            intakeTimes.forEach { intakeTime ->
//                repository.insertTime(
//                    IntakeTime(
//                        programIdFk = programFk, // Use the programId from the inserted program
//                        time = time,  // Use the specified time for each intake time
//                        intakeDate = intakeTime,
//                        status = Status.UPCOMING
//                    )
//                )
//            }
//            Log.d("Test Tag" , "Times Inserted")
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertProgram(
        medicineId: Int,
        programName: String,
        startDate: Date,
        weeks: Int,
        numPills: Int,
        time: LocalTime
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
                repository.insertProgram(program)
            } catch (e: Exception) {
                Log.e("Test Tag", "Insert Program failed: ${e.message}", e)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertIntakeTimes(programFk: Int, startDate: Date, weeks: Int, time: LocalTime) {
        val intakeTimes = generateIntakeTimes(startDate, weeks, time)
        Log.d("Test Tag", "$intakeTimes")

        viewModelScope.launch {
            intakeTimes.forEach { intakeTime ->
                try {
                    repository.insertTime(
                        IntakeTime(
                            programIdFk = programFk,
                            time = time,
                            intakeDate = intakeTime,
                            status = Status.UPCOMING
                        )
                    )
                } catch (e: Exception) {
                    Log.e("Test Tag", "Insert Intake Time failed: ${e.message}", e)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateIntakeTimes(startDate: Date, weeks: Int, time: LocalTime): List<Date> {
        val intakeTimesAndDates = mutableListOf<Date>()
        val calendar = Calendar.getInstance()
        calendar.time = startDate

        val hourOfDay = time.hour
        val minute = time.minute

        for (week in 0 until (weeks * 7)) {
            // Calculate the intake time based on the time, without an interval
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            val intakeTime = calendar.time
            intakeTimesAndDates.add(intakeTime)

            // Increment the calendar by one week for the next iteration
            calendar.add(Calendar.WEEK_OF_YEAR, 1)
        }

        return intakeTimesAndDates
    }
}

data class AddEditProgramState @RequiresApi(Build.VERSION_CODES.O) constructor(
    val medicine: Medicine = Medicine(999, "", 999, 999.9, false),
    val medicinePrograms: List<IntakeProgram> = emptyList(),
    val intakeTimes: List<IntakeTimesWithProgramAndMedicine> = emptyList(),
    val intakeTimeChecked: Boolean = false,
    val programs: List<IntakeProgram> = emptyList(),
    val timeList: List<IntakeTime> = emptyList(),
    val latestProgram: IntakeProgram = IntakeProgram(999, 999, "",
        Date(0),999, 999),
)
