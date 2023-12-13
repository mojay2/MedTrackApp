package com.example.medtrackbackend.ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.example.medtrackbackend.data.IntakeProgram

@Composable
fun ProgramItem(
    program: IntakeProgram,
    isActive: Boolean = false,
    onClick: () -> Unit
) {
    val containerColor =
        if (isActive) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.surface

    InfoCardExtended(
        infoTopLeft = {
            Text(
                text = program.programName,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
            )
        },
        infoTopRight = {
            Text(
                text = "Nov 15, 2023 - Jan 10, 2024",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        infoBottomLeft = {
            Text(
                text = "8 weeks | 6 weeks left",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        containerColor = containerColor,
        onClick = onClick
    )
}
