package com.example.medtrackbackend.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medtrackbackend.data.Medicine

@Composable
fun MedicationList(
    activeMedicine: Medicine,
    onActiveMedicineChange: (Medicine) -> Unit,
    isCabinet: Boolean = false,
    medications: List<Medicine>,
    navController: NavController
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(if (isCabinet) 0.dp else 16.dp)
    ) {
        if(medications.isEmpty()){
            item{
                Text(
                    text = "Your medicine cabinet is empty.",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        item {
            DisplayMedications(
                title = if (isCabinet) null else "To Take",
                medications = medications.filter { it.isActive },
                activeMedicine = activeMedicine,
                onActiveMedicineChange = onActiveMedicineChange,
                isCabinet = isCabinet,
                navController
            )
        }
        item {
            DisplayMedications(
                title = if (isCabinet) null else "Completed",
                medications = medications.filterNot { it.isActive },
                activeMedicine = activeMedicine,
                onActiveMedicineChange = onActiveMedicineChange,
                isCabinet = isCabinet,
                navController
            )
        }
    }
}

@Composable
private fun DisplayMedications(
    title: String?,
    medications: List<Medicine>,
    activeMedicine: Medicine,
    onActiveMedicineChange: (Medicine) -> Unit,
    isCabinet: Boolean,
    navController: NavController
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
            isCabinet = isCabinet,
            onClick = {
                if (isCabinet) {
                    navController.navigate("MedicineDetails/${medication.id}")
                } else {
                    //TODO ADD LOGIC FOR TAKING MEDICINE
                }
            },
            navController = navController,
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

//            onActiveMedicineChange(activeMedicineChange)
