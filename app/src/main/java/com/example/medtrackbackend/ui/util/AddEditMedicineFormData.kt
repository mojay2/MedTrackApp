package com.example.medtrackbackend.ui.util

data class AddEditMedicineFormData(
    val medicineName: String,
    val quantity: Int,
    val dosage: Double,
    val isActive: Boolean
)
