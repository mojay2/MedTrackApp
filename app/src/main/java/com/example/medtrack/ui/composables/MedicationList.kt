package com.example.medtrack.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.medtrack.data.model.Medication
import com.example.medtrack.data.util.sampleMedicationList

@Composable
fun MedicationList(
    activeMedicine: Medication?,
    onActiveMedicineChange: (Medication?) -> Unit,
    isCabinet: Boolean = false
) {
    val medications = sampleMedicationList
    val colGap = if (isCabinet) {
        0.dp
    } else {
        16.dp
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(colGap)
    ) {
        item {
            if (!isCabinet) {
                Text(
                    text = "To Take",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            medications.filter { it.isActive }.forEach { medication ->
                MedicationItem(
                    medication = medication,
                    isActive = medication.id == activeMedicine?.id,
                    isCabinet = isCabinet,
                ) {
                    onActiveMedicineChange(medication)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        item {
            if (!isCabinet) {
                Text(
                    text = "Completed",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            medications.filter { !it.isActive }.forEach { medication ->
                MedicationItem(
                    medication = medication,
                    isComplete = true,
                    isActive = medication.id == activeMedicine?.id,
                    isCabinet = isCabinet,
                ) {
                    onActiveMedicineChange(medication)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}