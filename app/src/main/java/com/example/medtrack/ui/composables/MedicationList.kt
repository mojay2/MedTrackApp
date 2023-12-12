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

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(if (isCabinet) 0.dp else 16.dp)
    ) {
        item {
            DisplayMedications(
                title = if (isCabinet) null else "To Take",
                medications = medications.filter { it.isActive },
                activeMedicine = activeMedicine,
                onActiveMedicineChange = onActiveMedicineChange,
                isCabinet = isCabinet
            )
        }
        item {
            DisplayMedications(
                title = if (isCabinet) null else "Completed",
                medications = medications.filterNot { it.isActive },
                activeMedicine = activeMedicine,
                onActiveMedicineChange = onActiveMedicineChange,
                isCabinet = isCabinet
            )
        }
    }
}

@Composable
private fun DisplayMedications(
    title: String?,
    medications: List<Medication>,
    activeMedicine: Medication?,
    onActiveMedicineChange: (Medication?) -> Unit,
    isCabinet: Boolean
) {
    if (title != null) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }

    medications.forEach { medication ->
        val isActiveMedicine = medication.id == activeMedicine?.id
        val activeMedicineChange = if (isActiveMedicine) null else medication
        MedicationItem(
            medication = medication,
            isActive = medication.id == activeMedicine?.id,
            isComplete = !medication.isActive,
            isCabinet = isCabinet
        ) {
            onActiveMedicineChange(activeMedicineChange)
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}