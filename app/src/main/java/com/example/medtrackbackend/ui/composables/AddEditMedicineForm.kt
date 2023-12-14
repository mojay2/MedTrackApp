package com.example.medtrackbackend.ui.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.medtrackbackend.data.Medicine
import com.example.medtrackbackend.ui.screens.add_edit_medicine.AddEditMedicineFloatingActionButton
import com.example.medtrackbackend.ui.screens.add_edit_medicine.AddEditMedicineViewModel
import com.example.medtrackbackend.ui.util.AddEditMedicineFormData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMedicineForm(
    medicine: Medicine,
    viewModel: AddEditMedicineViewModel
){
    // TODO: Maybe change these? Medyo panget itsura nung variables HAHAH. ewan ko kung me form helper sa kotlin or compose
//    var editedMedicineName by remember { mutableStateOf(medicine?.medicineName ?: "") }
//    var editedQuantity by remember { mutableStateOf(medicine?.quantity?.toString() ?: "") }
//    var editedDosage by remember { mutableStateOf(medicine?.dosage?.toString() ?: "") }
    var editedIsActive by remember { mutableStateOf(medicine?.isActive ?: false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = viewModel.state.editedName,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Medicine Name") },
            placeholder = { Text("Enter medicine name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = viewModel.state.editedQty,
            onValueChange = { viewModel.onQtyChange(it) },
            label = { Text("Quantity (pill)") },
            placeholder = { Text("Enter quantity") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
              keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = viewModel.state.editedDosage,
            onValueChange = { viewModel.onDosageChange(it) },
            label = { Text("Pill Dosage (mg)") },
            placeholder = { Text("Enter pill dosage") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
    }

    // Return the form data as an object
//    return AddEditMedicineFormData(
//        editedMedicineName,
//        editedQuantity.toInt(),
//        editedDosage.toDouble(),
//        editedIsActive
//    )
}

