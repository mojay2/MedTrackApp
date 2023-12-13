package com.example.medtrackbackend.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medtrackbackend.NavControllerProvider
import com.example.medtrackbackend.R
import com.example.medtrackbackend.data.Medicine

@Composable
fun MedicationItem(
    medication: Medicine,
    isComplete: Boolean = false,
    isActive: Boolean = false,
    isCabinet: Boolean = false,
    onClick: () -> Unit,
    navController: NavController
) {
    val containerColor = when {
        isComplete && !isCabinet -> MaterialTheme.colorScheme.surfaceColorAtElevation(
            elevation = 5.dp
        )

        isActive -> MaterialTheme.colorScheme.secondaryContainer
        else -> MaterialTheme.colorScheme.surface
    }

    val capsuleColor = if (isComplete && !isCabinet) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.primary
    }

    val hasOnClick = if (!isComplete) onClick else {
        {

        }
    }

    InfoCardExtended(
        infoTopLeft = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = medication.medicineName,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
                if (isComplete && !isCabinet) {
                    Image(
                        painter = painterResource(R.drawable.circle_check),
                        contentDescription = "Circle Check",
                    )
                }
            }
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
        onClick = hasOnClick
    )
}
