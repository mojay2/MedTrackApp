package com.example.medtrackbackend.ui.composables

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.medtrackbackend.data.IntakeTimesWithProgramAndMedicine
import com.example.medtrackbackend.data.Medicine
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun IntakeTimeCard(item: IntakeTimesWithProgramAndMedicine) {
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
            Text(text = item.intakeTime.id.toString())
            Text(text = item.medicine.medicineName)
            Text(text = item.intakeProgram.programName)
            Text(text = "${item.intakeProgram.numPills} pills")
            Text(text = "${item.intakeTime.intakeDate}")
            Text(text = item.intakeTime.time.format(DateTimeFormatter.ofPattern("HH:mm")))
        }
    }
}