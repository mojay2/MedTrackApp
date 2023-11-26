package com.example.medtrackbackend

import android.app.Application

class MedTrackApplication: Application() {
    override fun onCreate(){
        super.onCreate()
        Graph.provide(this)
    }
}