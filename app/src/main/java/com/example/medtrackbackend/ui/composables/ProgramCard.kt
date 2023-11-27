package com.example.medtrackbackend.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.medtrackbackend.data.IntakeProgram
import com.example.medtrackbackend.ui.screens.add_edit_medicine.formatDate

@Composable
fun ProgramCard(program: IntakeProgram){
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ){
        Text(text = "Program Name: ${program.programName}")
        Text(text = "${program.weeks} weeks")
        Text(text = "Start Date: ${ formatDate(program.startDate)}")
        Text(text = "${program.numPills} pills")

    }
}