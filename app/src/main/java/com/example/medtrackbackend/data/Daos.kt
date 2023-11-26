package com.example.medtrackbackend.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface MedicineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicine: Medicine)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(medicine: Medicine)

    @Delete
    suspend fun delete(medicine: Medicine)

    @Query("SELECT * FROM medicine WHERE medicine_id =:medicineId")
    fun getMedicine(medicineId: Int): Flow<Medicine>

    @Query("SELECT * FROM medicine")
    fun getAllMedicine(): Flow<List<Medicine>>

}

@Dao
interface ProgramDao {
    @Insert
    suspend fun insert(program: IntakeProgram)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(program: IntakeProgram)

    @Delete
    suspend fun delete(program: IntakeProgram)

    @Query("SELECT * FROM intake_program WHERE program_id =:programId")
    fun getProgram(programId: Int): Flow<IntakeProgram>

    @Query("SELECT * FROM intake_program")
    fun getAllPrograms(): Flow<List<IntakeProgram>>

    @Query("SELECT * FROM intake_program WHERE medIdFk = :medicineId")
    fun getProgramsForMedicine(medicineId: Int): Flow<List<IntakeProgram>>
}

@Dao
interface TimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(time: IntakeTime)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(time: IntakeTime)

    @Delete
    suspend fun delete(time: IntakeTime)

    @Query("SELECT * FROM  intake_time")
    fun getAllTimes(): Flow<List<IntakeTime>>

    @Query("SELECT * FROM intake_time WHERE intake_time_id = :timeId")
    fun getTimeById(timeId: Int): Flow<IntakeTime>

    @Query("SELECT * FROM intake_time WHERE programIdFk = :programId")
    fun getAllTimesForProgram(programId: Int): Flow<List<IntakeTime>>

    @Query("""
    SELECT *
    FROM intake_time
    INNER JOIN intake_program ON Intake_Time.programIdFk = Intake_Program.program_id
    INNER JOIN Medicine ON medIdFk = Medicine.medicine_id
    WHERE Intake_Time.programIdFk = :programId;
""")
    fun getAllTimesForProgramWithMedicine(programId: Long): Flow<List<IntakeTimesWithProgramAndMedicine>>

    @Query("""
        SELECT *
        FROM intake_time
        INNER JOIN intake_program ON Intake_Time.programIdFk = intake_time_id
        INNER JOIN Medicine ON medIdFk = medicine_id
        WHERE Intake_Time.intakeDate = :date;
    """)
    fun getAllTimesFromDate(date: Date): Flow<List<IntakeTimesWithProgramAndMedicine>>
}


data class IntakeTimesWithProgramAndMedicine(
    @Embedded val medicine: Medicine,
    @Embedded val intakeProgram: IntakeProgram,
    @Embedded val intakeTime: IntakeTime
)