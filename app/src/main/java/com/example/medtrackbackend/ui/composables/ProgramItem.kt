package com.example.medtrackbackend.ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.example.medtrackbackend.data.DateUtil
import com.example.medtrackbackend.data.IntakeProgram

@Composable
fun ProgramItem(
    program: IntakeProgram,
    isActive: Boolean = false,
    onClick: () -> Unit
) {
    val dateUtil = DateUtil()
    val containerColor =
        if (isActive) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.surface
    val endDate = dateUtil.calculateEndDate(program.startDate, program.weeks)
    val daysLeft = dateUtil.calculateDaysUntilEndDate(endDate)
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
                text = "${dateUtil.formatDateMMDDYYYY(program.startDate)} - " +
                        "${dateUtil.formatDateMMDDYYYY(endDate)}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        infoBottomLeft = {
            Text(
                text = "${program.weeks} weeks | $daysLeft days left",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        containerColor = containerColor,
        onClick = onClick
    )
}
