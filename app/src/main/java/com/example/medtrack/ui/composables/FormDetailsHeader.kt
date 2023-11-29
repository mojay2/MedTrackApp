package com.example.medtrack.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.example.medtrack.data.model.Medication

@Composable
fun FormDetailsHeader(
    medicine: Medication? = null,
    openDeleteDialog: MutableState<Boolean>,
    headerText: String,
    sideText: String,
) {
    Text(
        text = headerText,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
    )
    if (medicine != null) {
        Text(
            text = sideText,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .clickable { openDeleteDialog.value = true }
        )
    }
}