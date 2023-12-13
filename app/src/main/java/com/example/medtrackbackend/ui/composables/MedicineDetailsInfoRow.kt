package com.example.medtrackbackend.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.medtrackbackend.data.Medicine

@Composable
fun MedicineDetailsInfoRow(medicine: Medicine) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        InfoCard(
            infoTitle = "Quantity",
            infoText = "${medicine.quantity} pills left",
            modifier = Modifier.weight(1f)
        )
        InfoCard(
            infoTitle = "Pill Dosage",
            infoText = "${medicine.dosage} mg",
            modifier = Modifier.weight(1f)
        )
    }
}