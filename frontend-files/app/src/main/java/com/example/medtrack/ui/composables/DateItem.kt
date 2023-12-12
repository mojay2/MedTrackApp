package com.example.medtrack.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun DateItem(
    day: String,
    date: String,
    modifier: Modifier,
    isActive: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor =
        if (isActive) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    val textColor =
        if (isActive) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(all = 8.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = day,
            color = textColor,
            style = MaterialTheme.typography.labelSmall,
        )
        Text(
            text = date,
            color = textColor,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}
