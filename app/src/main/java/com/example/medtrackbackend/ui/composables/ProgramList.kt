package com.example.medtrackbackend.ui.composables

import android.util.Log
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
import com.example.medtrackbackend.data.DateUtil
import com.example.medtrackbackend.data.IntakeProgram
import com.example.medtrackbackend.ui.screens.medicine_details.MedicineDetailsViewModel
import java.time.LocalDate

@Composable
fun ProgramList(
    onActiveProgramChange: (IntakeProgram?) -> Unit,
    programs: List<IntakeProgram>,
    viewModel: MedicineDetailsViewModel
) {
    var activeProgram = viewModel.state.activeProgram
    val dateUtil = DateUtil()
    val currentDate = LocalDate.now() // Assuming your IntakeProgram's start date is of type LocalDate
    val active = programs.filter {
        (it.startDate == dateUtil.resetTimeToMidnight(currentDate.toDate())||
        it.startDate.before(currentDate.toDate())) &&
        dateUtil.calculateEndDate(it.startDate, it.weeks).after(currentDate.toDate())
    }
    val inactive = programs.filterNot { it in active }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ProgramListSection(
                title = "Active Programs",
                programs = active,
                activeProgram = activeProgram,
                onActiveProgramChange = onActiveProgramChange
            )
        }
        item {
            ProgramListSection(
                title = "Inactive Programs",
                programs = inactive,
                activeProgram = activeProgram,
                onActiveProgramChange = onActiveProgramChange
            )
        }
    }
}

@Composable
private fun ProgramListSection(
    title: String,
    programs: List<IntakeProgram>,
    activeProgram: IntakeProgram,
    onActiveProgramChange: (IntakeProgram?) -> Unit,
) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    if(programs.isEmpty()){
        if(title == "Active Programs"){
            Text(
                text = "No Active Programs.",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(
                text = "No Inactive Programs.",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    } else {
        programs.forEach { program ->
            val isActiveProgram = program.id == activeProgram.id
            val activeProgramChange = if (isActiveProgram) null else program

            ProgramItem(
                program = program,
                isActive = isActiveProgram
            ) {
                onActiveProgramChange(activeProgramChange)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}