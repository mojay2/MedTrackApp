package com.example.medtrack.data.model

data class Medication(
    val id: Int = 0,
    val medicineName: String,
    val quantity: Int,
    val dosage: Double,
    val isActive: Boolean
)