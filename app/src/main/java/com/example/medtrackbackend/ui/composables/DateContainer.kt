package com.example.medtrackbackend.ui.composables

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun DateContainer() {
    val state = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input,
        initialSelectedDateMillis = Calendar.getInstance().timeInMillis
    )
    var currentDate by remember { mutableStateOf(LocalDate.now()) }
    var activeDate by remember { mutableStateOf(LocalDate.now()) }
    val dateRange = (0..5).map { currentDate.plusDays(it.toLong()) }
    val openDialog = remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        modifier = Modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        DateSelectionHeader(currentDate, openDialog)
        DateRows(dateRange.take(3), activeDate) { activeDate = it }
        DateRows(dateRange.takeLast(3), activeDate) { activeDate = it }
    }

    if (openDialog.value) {
        DateDialog(
            openDialog = openDialog,
            state = state,
            onDateSelected = { selectedDate ->
                currentDate = selectedDate
                activeDate = selectedDate
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DateSelectionHeader(currentDate: LocalDate, openDialog: MutableState<Boolean>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        modifier = Modifier.clickable { openDialog.value = true }
    ) {
        Text(
            text = currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.labelMedium,
        )
        Icon(
            Icons.Filled.ArrowDropDown,
            contentDescription = "Arrow Down",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(16.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DateRows(
    dates: List<LocalDate>,
    activeDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        dates.forEach { date ->
            DateItem(
                day = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                date = date.dayOfMonth.toString(),
                modifier = Modifier.weight(1f),
                isActive = activeDate == date,
                onClick = { onDateSelected(date) }
            )
        }
    }
}