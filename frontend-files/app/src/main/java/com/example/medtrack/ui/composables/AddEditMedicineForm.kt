package com.example.medtrack.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.medtrack.data.model.Medication

@Composable
fun AddEditMedicineForm(
    medicine: Medication? = null,
) {
    // TODO: Maybe change these? Medyo panget itsura nung variables HAHAH. ewan ko kung me form helper sa kotlin or compose
    var editedMedicineName by remember { mutableStateOf(medicine?.medicineName ?: "") }
    var editedQuantity by remember { mutableStateOf(medicine?.quantity?.toString() ?: "") }
    var editedDosage by remember { mutableStateOf(medicine?.dosage?.toString() ?: "") }
    var editedIsActive by remember { mutableStateOf(medicine?.isActive ?: false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = editedMedicineName,
            onValueChange = { editedMedicineName = it },
            label = { Text("Medicine Name") },
            placeholder = { Text("Enter medicine name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = editedQuantity,
            onValueChange = { editedQuantity = it },
            label = { Text("Quantity (pill)") },
            placeholder = { Text("Enter quantity") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = editedDosage,
            onValueChange = { editedDosage = it },
            label = { Text("Pill Dosage (mg)") },
            placeholder = { Text("Enter pill dosage") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}