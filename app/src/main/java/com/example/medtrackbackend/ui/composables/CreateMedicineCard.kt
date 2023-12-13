package com.example.medtrackbackend.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.medtrackbackend.data.Medicine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMedicineCard(
    onAddMedicine: (medicineName: String, qty:Int, dosage: Double) -> Unit,
    onEditMedicine: (id:Int, medicineName: String, qty:Int, dosage: Double) -> Unit,
    medicine: Medicine
) {
    var isEditing = false
    if(medicine.id != 999)
        isEditing = true

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
//            var medicineName by rememberSaveable(key = isEditing) { mutableStateOf(if (isEditing) medicine.medicineName else "") }
            var medicineName by rememberSaveable{ mutableStateOf(if(isEditing) medicine.medicineName else "") }
            var qty by rememberSaveable { mutableStateOf(if(isEditing) medicine.quantity.toString() else "") }
            var dosage by rememberSaveable { mutableStateOf(if(isEditing) medicine.dosage.toString() else "") }

//            var medicineName by rememberSaveable { mutableStateOf("") }
//            var qty by rememberSaveable { mutableStateOf("") }
//            var dosage by rememberSaveable { mutableStateOf("") }

            TextField(
                value = medicineName,
                onValueChange = { medicineName = it },
                label = { Text("Medicine Name") }
            )

            TextField(
                value = qty,
                onValueChange = {
                    // Ensure the input is a number
                    if (it.isDigitsOnly()) {
                        qty = it
                    }
                },
                label = { Text("Quantity") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            TextField(
                value = dosage,
                onValueChange = {
                    // Ensure the input is a number
                    if (it.isDigitsOnly()) {
                        dosage = it
                    }
                },
                label = { Text("Dosage") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            Button(
                onClick = {
                    if (medicineName.isNotBlank() && dosage.isNotBlank() && qty.isNotBlank()) {
                        val dosageValue = dosage.toDouble()
                        val qtyValue = qty.toInt()

                        if(isEditing)
                            onEditMedicine(medicine.id, medicineName, qtyValue, dosageValue)
                        else
                            onAddMedicine(medicineName, qtyValue,dosageValue)

                        medicineName = ""
                        qty = ""
                        dosage = ""
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
            ) {
                if(isEditing)
                    Text("Update Medicine")
                else
                    Text("Add Medicine")
            }
        }
    }
}