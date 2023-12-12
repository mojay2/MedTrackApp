package com.example.medtrack.data.model

import java.util.Date

data class IntakeProgram(
    val id: Int = 0,
    val medIdFk: Int,
    val programName: String,
    val startDate: Date,
    val weeks: Int,
    val numPills: Int
)
