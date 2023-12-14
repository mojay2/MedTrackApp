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
import java.lang.IllegalStateException
import java.util.Date

class Repository(
    private val medicineDao: MedicineDao,
    private val intakeProgramDao: ProgramDao,
    private val intakeTimeDao: TimeDao
) {
    val allMedicine: Flow<List<Medicine>> = medicineDao.getAllMedicine()
    val allPrograms: Flow<List<IntakeProgram>> = intakeProgramDao.getAllPrograms()

    suspend fun insertMedicine(medicine: Medicine) {
        medicineDao.insert(medicine)
    }

    suspend fun insertProgram(program: IntakeProgram):Long {
         return intakeProgramDao.insert(program)
    }

    suspend fun insertTime(time: IntakeTime) {
        intakeTimeDao.insert(time)
    }

    suspend fun updateMedicineStatus(medicineId: Int, isActive: Boolean){
        medicineDao.updateMedicineStatus(medicineId, isActive)
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

    suspend fun deleteMedicineFromId(medicineId: Int){
        medicineDao.deleteMedicineFromId(medicineId)
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

    fun getProgramsFromMedicine(medicineId: Int): Flow<List<IntakeProgram>>{
        return intakeProgramDao.getProgramsForMedicine(medicineId)
    }

    fun getAllTimesFromDate(date: Date): Flow<List<IntakeTimesWithProgramAndMedicine>> {
        return intakeTimeDao.getAllTimesFromDate(date)
    }


}
