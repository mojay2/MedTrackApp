package com.example.medtrackbackend.ui.composables

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medtrackbackend.R
import com.example.medtrackbackend.data.IntakeTimesWithProgramAndMedicine
import com.example.medtrackbackend.data.Medicine
import com.example.medtrackbackend.data.Status
import com.example.medtrackbackend.ui.screens.home.HomeViewModel

@Composable
fun HomeMedicationList(
    medications: List<IntakeTimesWithProgramAndMedicine>,
    navController: NavController,
    viewModel: HomeViewModel
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HomeDisplayMedications(
                title = "To Take",
                medications = medications.filter { it.intakeTime.status == Status.UPCOMING },
                navController,
                viewModel = viewModel
            )
        }
        item {
            HomeDisplayMedications(
                title = "Completed",
                medications = medications.filter { it.intakeTime.status == Status.TAKEN },
                navController,
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun HomeDisplayMedications(
    title: String?,
    medications: List<IntakeTimesWithProgramAndMedicine>,
    navController: NavController,
    viewModel: HomeViewModel
) {
    val context = LocalContext.current

    if (title != null) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
    if(medications.isEmpty()){
        if(title === "To Take")
            Text(
                text = "No Medicine to Take Today.",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        else
            Text(
                text = "You have not taken any medication today.",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
    } else {
        medications.forEach { medication ->
            val openConfirmDialog = remember { mutableStateOf(false) }
            if (openConfirmDialog.value) {
                ConfirmModal(
                    onDismissRequest = { openConfirmDialog.value = false },
                    onConfirmation = {
                        if(viewModel.validateInventory(medication)){
                            if(viewModel.isFutureDate(medication)){
                                Toast.makeText(context, "Cannot Take Medicine from a future date", Toast.LENGTH_SHORT).show()
                            }else{
                                viewModel.tookMedicine(medication)
                                Toast.makeText(context, "Medicine Intake Success", Toast.LENGTH_SHORT).show()
                                openConfirmDialog.value = false
                            }
                        }else {
                            Toast.makeText(context, "Cannot Take Medicine. Inventory Too Low", Toast.LENGTH_SHORT).show()
                        }
                    },
                    dialogTitle = "Confirm Dosage",
                    dialogContent = {
                        Text(
                            text = "Medicine: ${medication.medicine.medicineName}\n" +
                                    "Dosage: ${medication.intakeProgram.numPills} pills\n" +
                                    "Time: ${medication.intakeTime.time}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    icon = Icons.Filled.Check
                )
            }
            HomeMedicationItem(
                medication = medication,
                isComplete = medication.intakeTime.status == Status.TAKEN,
                navController = navController,
                onClick = {
                    if(viewModel.isLowInStock(medication))
                        Toast.makeText(context, "This Medicine will run out soon.", Toast.LENGTH_SHORT).show()
                    openConfirmDialog.value = true
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun HomeMedicationItem(
    medication: IntakeTimesWithProgramAndMedicine,
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
                    text = medication.medicine.medicineName,
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
            LabelText(text = medication.intakeTime.time.toString())
        },
        infoBottomLeft = {
            LabelText(text = "${medication.intakeProgram.numPills} pills")
        },
        containerColor = containerColor,
        icon = R.drawable.capsule,
        iconColor = capsuleColor,
        onClick = hasOnClick
    )
}
