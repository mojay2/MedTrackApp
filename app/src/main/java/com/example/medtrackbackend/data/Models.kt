package com.example.medtrackbackend.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.Date

@Entity(tableName = "medicine")
data class Medicine(
    @ColumnInfo(name = "medicine_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val medicineName: String,
    val quantity: Int,
    val dosage: Double,
    val isActive: Boolean
)

@Entity(
    tableName = "intake_program",
    foreignKeys = [ForeignKey(
        entity = Medicine::class,
        parentColumns = ["medicine_id"],
        childColumns = ["medIdFk"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("medIdFk")]
)
data class IntakeProgram(
    @ColumnInfo(name = "program_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val medIdFk: Int, // Foreign key reference to Medicine table
    val programName: String,
    val startDate: Date,
    val weeks: Int,
    val numPills: Int
)

@Entity(tableName = "intake_time",
    foreignKeys = [ForeignKey(
        entity = IntakeProgram::class,
        parentColumns = ["program_id"],
        childColumns = ["programIdFk"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("programIdFk")]
)
data class IntakeTime(
    @ColumnInfo(name = "intake_time_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val programIdFk: Int, // Foreign key reference to Program table
    val time: String,
    val intakeDate: Date,
    val status: Status
)

enum class Status {
    TAKEN,
    UPCOMING,
    MISSED
}