package com.example.medtrack.data.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class EditableDate {
    data class Date(val value: java.util.Date) : EditableDate()
    data class StringDate(val value: String) : EditableDate()
}

fun convertDateToString(date: Date): String {
    val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    return dateFormat.format(date)
}