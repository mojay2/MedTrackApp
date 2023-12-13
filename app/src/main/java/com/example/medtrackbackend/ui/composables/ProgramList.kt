package com.example.medtrackbackend.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.medtrackbackend.data.IntakeProgram

@Composable
fun ProgramList(
    activeProgram: IntakeProgram,
    onActiveProgramChange: (IntakeProgram?) -> Unit,
    programs: List<IntakeProgram>
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ProgramListSection(
                title = "Active Program",
                programs = programs.take(1),
                activeProgram = activeProgram,
                onActiveProgramChange = onActiveProgramChange
            )
        }
        item {
            ProgramListSection(
                title = "Inactive Programs",
                programs = programs.subList(1, programs.size),
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
    activeProgram: IntakeProgram?,
    onActiveProgramChange: (IntakeProgram?) -> Unit,
) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    programs.forEach { program ->
        val isActiveProgram = program.id == activeProgram?.id
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