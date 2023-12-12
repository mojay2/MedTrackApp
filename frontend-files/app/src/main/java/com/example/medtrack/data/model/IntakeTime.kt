package com.example.medtrack.data.model

import com.example.medtrack.data.util.DaysOfTheWeek
import com.example.medtrack.data.util.Status

data class IntakeTime(
    val id: Int = 0,
    val programIdFk: Int,
    val time: String,
    val intakeDate: DaysOfTheWeek,
    val status: Status,
)
