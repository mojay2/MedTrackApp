package com.example.medtrackbackend.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.example.medtrackbackend.data.DateUtil
//import com.example.medtrack.data.util.EditableDate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DateToTextField(
    editedStartDate: Date,
    onClick: () -> Unit,
) {
//    val formattedDate = when (editedStartDate) {
//        is EditableDate.Date -> {
//            val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
//            dateFormat.format((editedStartDate as EditableDate.Date).value)
//        }
//
//        is EditableDate.StringDate -> (editedStartDate as EditableDate.StringDate).value
//    }
    val dateUtil = DateUtil()
    OutlinedTextField(
        value = dateUtil.formatDateMMDDYYYY(editedStartDate),
        readOnly = true,
        onValueChange = {},
        label = { Text("Start Date") },
        placeholder = { Text("Enter start date") },
        trailingIcon = {
            Icon(
                Icons.Filled.CalendarToday,
                contentDescription = "Calendar Icon",
                modifier = Modifier.clickable(onClick = onClick)
            )
        },
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
    )
}