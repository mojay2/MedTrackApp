package com.example.medtrack.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medtrack.R
import com.example.medtrack.ui.util.UiEvent

@Composable
fun HomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
) {
    val painter: Painter = painterResource(R.drawable.home)
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Hello there,",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Here are your meds for the day.",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
            Column() {
                Image(
                    painter = painter,
                    contentDescription = "Home Art",
                    modifier = Modifier
                        .size(300.dp)
                )

            }
        }
    }
}

@Composable
fun MedicationSchedule() {
    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val medicationsToTake = listOf(
        Medication("Paracetamol", "6:00 am", "1 pill"),
        Medication("Paracetamol", "12:00 pm", "1 pill"),
        Medication("Paracetamol", "8:00 pm", "1 pill")
    )
    val completedMedications = listOf(
        Medication("Paracetamol", "6:00 am", "1 pill"),
        Medication("Paracetamol", "12:00 pm", "1 pill"),
        Medication("Paracetamol", "6:00 am", "1 pill")
    )

    LazyColumn {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (day in daysOfWeek) {
                    Text(text = day)
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            medicationsToTake.forEach {
                MedicationItem(it)
            }
        }
        item {
            Text(text = "Completed")
        }
        item {
            completedMedications.forEach {
                MedicationItem(it)
            }
        }
    }
}

@Composable
fun MedicationItem(medication: Medication) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = medication.name)
        Text(text = "${medication.time} - ${medication.dosage}")
    }
}

data class Medication(val name: String, val time: String, val dosage: String)

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onNavigate = {})
}