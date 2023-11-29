package com.example.medtrack.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.medtrack.data.model.IntakeProgram
import com.example.medtrack.data.model.Medication

@Composable
fun FormDetailsHeader(
    medicine: Medication? = null,
    program: IntakeProgram? = null,
    openDeleteDialog: MutableState<Boolean>,
    headerText: String,
    sideText: String,
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = headerText,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        if (medicine != null || program != null) {
            Text(
                text = sideText,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .clickable { openDeleteDialog.value = true }
            )
        }
    }
}