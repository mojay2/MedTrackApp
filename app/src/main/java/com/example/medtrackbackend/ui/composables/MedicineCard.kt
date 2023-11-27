package com.example.medtrackbackend.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medtrackbackend.data.Medicine

@Composable
fun MedicineCard(medicine: Medicine, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("MedicineDetails/${medicine.id}") }

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Medicine Name: ${medicine.medicineName}")
            Text(text = "Dosage: ${medicine.dosage} mg")
            Text(text = "${medicine.quantity} pills left. ")
        }
    }
}