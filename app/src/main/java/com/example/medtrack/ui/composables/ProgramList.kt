package com.example.medtrack.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.medtrack.data.model.IntakeProgram
import com.example.medtrack.data.util.sampleProgramList

@Composable
fun ProgramList(
    activeProgram: IntakeProgram?,
    onActiveProgramChange: (IntakeProgram?) -> Unit,
) {
    val programs = sampleProgramList
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // TODO: Add logic for conditional rendering
        if (true) {
            item {
                SectionTitle(text = "Active Program")
                programs.take(1).forEach { program ->
                    val isActiveProgram = program.id == activeProgram?.id
                    val activeProgramChange = if (isActiveProgram) null else program
                    ProgramItem(
                        program = program,
                        isActive = program.id == activeProgram?.id
                    )
                    {
                        onActiveProgramChange(activeProgramChange)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        if (true) {
            item {
                SectionTitle(text = "Inactive Programs")
                programs.subList(1, programs.size).forEach { program ->
                    val isActiveProgram = program.id == activeProgram?.id
                    val activeProgramChange = if (isActiveProgram) null else program
                    ProgramItem(
                        program = program,
                        isActive = program.id == activeProgram?.id
                    )
                    {
                        onActiveProgramChange(activeProgramChange)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}