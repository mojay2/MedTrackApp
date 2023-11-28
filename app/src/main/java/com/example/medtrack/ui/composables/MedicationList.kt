package com.example.medtrack.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medtrack.data.model.Medication
import com.example.medtrack.data.util.sampleMedicationList
import com.example.medtrack.ui.theme.MedTrackTheme

@Composable
fun MedicationList(
    activeMedicine: Medication?,
    onActiveMedicineChange: (Medication?) -> Unit
) {
    val medications = sampleMedicationList

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionTitle(text = "To Take")
            medications.filter { it.isActive }.forEach { medication ->
                MedicationItem(
                    medication = medication,
                    isActive = medication.id == activeMedicine?.id
                ) {
                    onActiveMedicineChange(medication)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        item {
            SectionTitle(text = "Completed")
            medications.filter { !it.isActive }.forEach { medication ->
                MedicationItem(
                    medication = medication,
                    isComplete = true,
                    isActive = medication.id == activeMedicine?.id
                ) {
                    onActiveMedicineChange(medication)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFDDE3EA,
    heightDp = 400
)
@Composable
fun MedicineListPreview() {
    MedTrackTheme {
        Column(modifier = Modifier.padding(8.dp)) {
            MedicationList(
                activeMedicine = null, onActiveMedicineChange = {}
            )
        }
    }
}