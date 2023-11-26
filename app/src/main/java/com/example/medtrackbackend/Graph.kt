package com.example.medtrackbackend

import android.content.Context
import com.example.medtrackbackend.data.MedTrackDatabase
import com.example.medtrackbackend.ui.repository.Repository

object Graph {
    lateinit var db: MedTrackDatabase
        private set

    val repository by lazy {
        Repository(
            medicineDao = db.medicineDao(),
            intakeProgramDao = db.programDao(),
            intakeTimeDao = db.timeDao()
        )
    }

    fun provide(context: Context){
        db = MedTrackDatabase.getDatabase(context)
    }
}