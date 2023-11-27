package com.example.medtrackbackend.ui.screens.add_edit_program

import android.util.Log
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

    fun insertProgram(
        medicineId: Int,
        programName: String,
        startDate: Date,
        weeks: Int,
        numPills: Int,
        interval: Int,
        time: String
    ) {
        viewModelScope.launch {
            // Insert the program
            val programId = repository.insertProgram(
                IntakeProgram(
                    medIdFk = medicineId,
                    programName = programName,
                    startDate = startDate,
                    weeks = weeks,
                    numPills = numPills,
                )
            )

            Log.d("Test Tag" , "Program Inserted")
            // Generate intake times
            val intakeTimes = generateIntakeTimes(startDate, weeks, interval, time)
            Log.d("Test Tag" , intakeTimes.toString())
            val newIndex = countTimeEntries()
            Log.d("Test Tag" , newIndex.toString())

            // Insert intake times into the database
            intakeTimes.forEach { intakeTime ->
                repository.insertTime(
                    IntakeTime(
                        programIdFk = newIndex, // Use the programId from the inserted program
                        time = time,  // Use the specified time for each intake time
                        intakeDate = intakeTime,
                        status = Status.UPCOMING
                    )
                )
            }
            Log.d("Test Tag" , "Times Inserted")

        }
    }

    private suspend fun countTimeEntries(): Int {
        return repository.getAllTimes().count()
    }

    private fun generateIntakeTimes(startDate: Date, weeks: Int, interval: Int, time: String): List<Date> {
        val intakeTimesAndDates = mutableListOf<Date>()
        val calendar = Calendar.getInstance()
        calendar.time = startDate

        val timeParts = time.split(":")
        val hourOfDay = timeParts[0].toInt()
        val minute = timeParts[1].toInt()

        for (week in 0 until weeks) {
            for (day in 0 until 7) {
                // Calculate the intake time based on the day and interval
                calendar.add(Calendar.DAY_OF_MONTH, day)
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                val intakeTime = calendar.time
                intakeTimesAndDates.add(intakeTime)

                // Increment the calendar by the interval for the next day
                calendar.add(Calendar.DAY_OF_MONTH, interval - day)
            }
        }

        return intakeTimesAndDates
    }
}

data class AddEditProgramState(
    val medicine: Medicine = Medicine(999, "", 999, 999.9, false),
    val medicinePrograms: List<IntakeProgram> = emptyList(),
    val intakeTimes: List<IntakeTimesWithProgramAndMedicine> = emptyList(),
    val intakeTimeChecked: Boolean = false,
    val programs: List<IntakeProgram> = emptyList()
)
