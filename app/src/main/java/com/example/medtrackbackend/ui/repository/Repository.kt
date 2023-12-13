package com.example.medtrackbackend.ui.repository

import com.example.medtrackbackend.data.IntakeProgram
import com.example.medtrackbackend.data.IntakeTime
import com.example.medtrackbackend.data.IntakeTimesWithProgramAndMedicine
import com.example.medtrackbackend.data.Medicine
import com.example.medtrackbackend.data.MedicineDao
import com.example.medtrackbackend.data.ProgramDao
import com.example.medtrackbackend.data.Status
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
    val allTimes: Flow<List<IntakeTime>> = intakeTimeDao.getAllTimes()

    val latestProgram: Flow<IntakeProgram> = intakeProgramDao.getLatestProgram()

//    suspend fun getAllTimes():Flow<List<IntakeTime>>{
//        return intakeTimeDao.getAllTimes()
//    }

    suspend fun insertMedicine(medicine: Medicine) {
        medicineDao.insert(medicine)
    }

    suspend fun insertProgram(program: IntakeProgram):Long {
         return intakeProgramDao.insert(program)
    }

    suspend fun insertTime(time: IntakeTime) {
        intakeTimeDao.insert(time)
    }

    suspend fun updateIntakeTimeStatus(time: IntakeTime) {
        intakeTimeDao.updateIntakeTimeStatus(time)
    }

    suspend fun updateMedicine(medicine: Medicine){
        medicineDao.update(medicine = medicine)
    }

    suspend fun updateProgram(program: IntakeProgram) {
        intakeProgramDao.update(program)
    }

    suspend fun deleteProgramFromId(programId: Int){
        intakeProgramDao.deleteProgramFromId(programId)
    }

    suspend fun deleteAllTimesFromProgramId(programId: Int){
        intakeTimeDao.deleteAllTimesFromProgramId(programId)
    }

    fun getMedicineById(medicineId: Int): Flow<Medicine> {
        return medicineDao.getMedicine(medicineId)
    }

    fun getProgramById(programId: Int): Flow<IntakeProgram> {
        return intakeProgramDao.getProgram(programId)
    }

//    fun getLatestProgram():Flow<IntakeProgram>{
//        return intakeProgramDao.getLatestProgram()
//    }

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
