package com.example.medtrack.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.medtrack.R
import com.example.medtrack.data.model.Medication

@Composable
fun MedicationItem(
    medication: Medication,
    isComplete: Boolean = false,
    isActive: Boolean = false,
    isCabinet: Boolean = false,
    onClick: () -> Unit
) {
    val containerColor =
        if (isComplete && !isCabinet) MaterialTheme.colorScheme.surfaceColorAtElevation(
            elevation = 5.dp
        )
        else if (isActive) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.surface

    val capsuleColor =
        if (isComplete && !isCabinet) MaterialTheme.colorScheme.secondary
        else MaterialTheme.colorScheme.primary

    val hasOnclick = if (!isComplete) {
        onClick
    } else {
        {}
    }

    InfoCardExtended(
        infoTopLeft = {
            Text(
                text = medication.medicineName,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
            )
            if (isComplete && !isCabinet)
                Image(
                    painter = painterResource(R.drawable.circle_check),
                    contentDescription = "Circle Check",
                )
        },
        infoTopRight = {
            if (isCabinet) {
                MedicineTag(medication)
            } else {
                LabelText(text = "6:00 am")
            }
        },
        infoBottomLeft = {
            if (isCabinet) {
                LabelText(text = medication.quantity.toString() + " pills left")
            } else {
                LabelText(text = "1 pill")
            }
        },
        containerColor = containerColor,
        icon = R.drawable.capsule,
        iconColor = capsuleColor,
        onClick = hasOnclick
    )
}
