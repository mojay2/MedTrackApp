package com.example.medtrack.data.util

import com.example.medtrack.data.model.IntakeProgram
import java.util.Date

val sampleProgramList = listOf(
    IntakeProgram(
        id = 0,
        medIdFk = 1,
        programName = "Program 1",
        startDate = Date(),
        weeks = 4,
        numPills = 3
    ),
    IntakeProgram(
        id = 1,
        medIdFk = 2,
        programName = "Program 2",
        startDate = Date(),
        weeks = 3,
        numPills = 2
    ),
    IntakeProgram(
        id = 2,
        medIdFk = 3,
        programName = "Program 3",
        startDate = Date(),
        weeks = 6,
        numPills = 1
    ),
    IntakeProgram(
        id = 3,
        medIdFk = 4,
        programName = "Program 4",
        startDate = Date(),
        weeks = 5,
        numPills = 4
    ),
    IntakeProgram(
        id = 4,
        medIdFk = 5,
        programName = "Program 5",
        startDate = Date(),
        weeks = 2,
        numPills = 5
    )
)