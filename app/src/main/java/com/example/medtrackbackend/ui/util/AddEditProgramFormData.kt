package com.example.medtrackbackend.ui.util

import java.time.LocalTime
import java.util.Date

data class AddEditProgramFormData (
    val programName: String,
    val startDate: Date,
    val weeks: Int,
    val numPills: Int,
    val time: LocalTime
)