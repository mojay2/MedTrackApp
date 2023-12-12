package com.example.medtrack.ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun LabelText(
    text: String,
    color: Color = MaterialTheme.colorScheme.secondary,
    size: TagSize = TagSize.SMALL
) {
    val tagStyle = when (size) {
        TagSize.SMALL -> MaterialTheme.typography.labelSmall
        TagSize.LARGE -> MaterialTheme.typography.labelMedium
    }
    Text(
        text = text,
        color = color,
        style = tagStyle,
    )
}