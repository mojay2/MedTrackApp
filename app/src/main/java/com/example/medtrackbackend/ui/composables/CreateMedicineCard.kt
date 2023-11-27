package com.example.medtrackbackend.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMedicineCard(
    onAddMedicine: (medicineName: String, qty:Int, dosage: Double) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            var medicineName by rememberSaveable { mutableStateOf("") }
            var qty by rememberSaveable { mutableStateOf("") }
            var dosage by rememberSaveable { mutableStateOf("") }

            TextField(
                value = medicineName,
                onValueChange = { medicineName = it },
                label = { Text("Medicine Name") }
            )

            TextField(
                value = qty,
                onValueChange = { qty = it },
                label = { Text("Quantity") }
            )

            TextField(
                value = dosage,
                onValueChange = { dosage = it },
                label = { Text("Dosage") }
            )

            Button(
                onClick = {
                    // Perform validation and add the medicine to the database
                    if (medicineName.isNotBlank() && dosage.isNotBlank() && qty.isNotBlank()) {
                        // Convert dosage to Double
                        val dosageValue = dosage.toDouble()
                        val qtyValue = qty.toInt()
                        // Call the provided callback to add the medicine to the database
                        onAddMedicine(medicineName, qtyValue,dosageValue)

                        // Clear the input fields after adding the medicine
                        medicineName = ""
                        qty = ""
                        dosage = ""
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
            ) {
                Text("Add Medicine")
            }
        }
    }
}