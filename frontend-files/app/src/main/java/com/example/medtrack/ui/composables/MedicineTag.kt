package com.example.medtrack.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.medtrack.data.model.Medication


enum class TagSize {
    SMALL, LARGE
}

@Composable
fun MedicineTag(
    medication: Medication,
    size: TagSize = TagSize.SMALL
) {
    val tagContent = if (medication.isActive) "active" else "inactive"

    val tagColor =
        if (medication.isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer

    val labelColor =
        if (medication.isActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(tagColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        LabelText(text = tagContent, color = labelColor, size = size)
    }
}