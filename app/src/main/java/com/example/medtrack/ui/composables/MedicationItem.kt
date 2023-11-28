package com.example.medtrack.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.medtrack.R
import com.example.medtrack.data.model.Medication

@Composable
fun MedicationItem(
    medication: Medication,
    isComplete: Boolean = false,
    isActive: Boolean = false,
    onClick: () -> Unit
) {
    val containerColor =
        if (isComplete) MaterialTheme.colorScheme.surfaceColorAtElevation(
            elevation = 5.dp
        )
        else if (isActive) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.surface

    val capsuleColor =
        if (isComplete) MaterialTheme.colorScheme.secondary
        else MaterialTheme.colorScheme.primary

    val clickableModifier = if (!isComplete) {
        Modifier.clickable(onClick = onClick)
    } else {
        Modifier
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(containerColor)
            .padding(8.dp)
            .then(clickableModifier)
    ) {
        Icon(
            painter = painterResource(R.drawable.capsule),
            contentDescription = "capsule",
            tint = capsuleColor,
        )
        Column() {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TitleText(medication.medicineName)
                    if (!medication.isActive)
                        Image(
                            painter = painterResource(R.drawable.circle_check),
                            contentDescription = "Circle Check",
                        )
                }
                LabelText(text = "6:00 am")
            }
            LabelText(text = "1 pill")
        }
    }
}
