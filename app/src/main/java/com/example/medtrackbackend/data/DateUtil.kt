package com.example.medtrackbackend.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale

class DateUtil {

    @RequiresApi(Build.VERSION_CODES.O)
    fun asDate(localDate: LocalDate): Date {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun asDate(localDateTime: LocalDateTime): Date {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun asLocalDate(date: Date): LocalDate {
        return Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun asLocalDateTime(date: Date): LocalDateTime {
        return Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun resetTimeToMidnight(date: Date): Date {
        val localDate = asLocalDate(date)
        val localDateTime = LocalDateTime.of(localDate, LocalTime.MIDNIGHT)
        return asDate(localDateTime)
    }

    fun formatDateMMDDYYYY(date: Date): String {
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateEndDate(startDate: Date, weeks: Int): Date {
        val localStartDate = asLocalDate(startDate)
        val endDate = localStartDate.plusWeeks(weeks.toLong())
        return asDate(endDate)
    }
    
    fun calculateDaysUntilEndDate(endDate: Date): Int {
        val today = LocalDate.now()
        val localEndDate = asLocalDate(endDate)
        return today.until(localEndDate, ChronoUnit.DAYS).toInt()
    }
}