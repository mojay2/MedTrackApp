package com.example.medtrack.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.medtrack.data.model.IntakeProgram
import com.example.medtrack.data.model.Medication

enum class FormHeaderSize {
    SMALL, LARGE
}

@Composable
fun FormDetailsHeader(
    medicine: Medication? = null,
    program: IntakeProgram? = null,
    headerText: String,
    subHeaderText: String,
    subHeaderOnClick: () -> Unit = {},
    size: FormHeaderSize = FormHeaderSize.LARGE,
    showSubHeader: Boolean = false,
) {
    val formHeaderSize = when (size) {
        FormHeaderSize.SMALL -> MaterialTheme.typography.bodySmall
        FormHeaderSize.LARGE -> MaterialTheme.typography.titleMedium
    }

    val formSubHeaderSize = when (size) {
        FormHeaderSize.SMALL -> MaterialTheme.typography.bodySmall
        FormHeaderSize.LARGE -> MaterialTheme.typography.labelLarge
    }

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = headerText,
            style = formHeaderSize,
            color = MaterialTheme.colorScheme.onSurface,
        )
        if (medicine != null || program != null || showSubHeader) {
            Text(
                text = subHeaderText,
                style = formSubHeaderSize,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .clickable { subHeaderOnClick() }

            )
        }
    }
}