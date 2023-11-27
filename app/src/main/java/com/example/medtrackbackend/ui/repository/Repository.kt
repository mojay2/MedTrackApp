package com.example.medtrackbackend.ui.repository

import com.example.medtrackbackend.data.IntakeProgram
import com.example.medtrackbackend.data.IntakeTime
import com.example.medtrackbackend.data.IntakeTimesWithProgramAndMedicine
import com.example.medtrackbackend.data.Medicine
import com.example.medtrackbackend.data.MedicineDao
import com.example.medtrackbackend.data.ProgramDao
import com.example.medtrackbackend.data.TimeDao
import kotlinx.coroutines.flow.Flow
import java.util.Date

class Repository(
    private val medicineDao: MedicineDao,
    private val intakeProgramDao: ProgramDao,
    private val intakeTimeDao: TimeDao
) {
    val allMedicine: Flow<List<Medicine>> = medicineDao.getAllMedicine()
    val allPrograms: Flow<List<IntakeProgram>> = intakeProgramDao.getAllPrograms()
//    val allTimes: Flow<List<IntakeTime>> = intakeTimeDao.getAllTimes()


    fun getAllTimes():Flow<List<IntakeTime>>{
        return intakeTimeDao.getAllTimes()
    }

    suspend fun insertMedicine(medicine: Medicine) {
        medicineDao.insert(medicine)
    }

    suspend fun insertProgram(program: IntakeProgram) {
        intakeProgramDao.insert(program)
    }

    suspend fun insertTime(time: IntakeTime) {
        intakeTimeDao.insert(time)
    }


    fun getMedicineById(medicineId: Int): Flow<Medicine> {
        return medicineDao.getMedicine(medicineId)
    }

    fun getProgramById(programId: Int): Flow<IntakeProgram> {
        return intakeProgramDao.getProgram(programId)
    }

    fun getTimeById(timeId: Int): Flow<IntakeTime> {
        return intakeTimeDao.getTimeById(timeId)
    }

    fun getProgramsFromMedicine(medicineId: Int): Flow<List<IntakeProgram>>{
        return intakeProgramDao.getProgramsForMedicine(medicineId)
    }

    fun getAllTimesForProgram(programId: Int): Flow<List<IntakeTime>> {
        return intakeTimeDao.getAllTimesForProgram(programId)
    }

    fun getAllTimesForProgramWithMedicine(programId: Long): Flow<List<IntakeTimesWithProgramAndMedicine>> {
        return intakeTimeDao.getAllTimesForProgramWithMedicine(programId)
    }

    fun getAllTimesFromDate(date: Date): Flow<List<IntakeTimesWithProgramAndMedicine>> {
        return intakeTimeDao.getAllTimesFromDate(date)
    }
}
