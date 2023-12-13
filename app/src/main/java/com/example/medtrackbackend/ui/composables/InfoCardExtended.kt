package com.example.medtrackbackend.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medtrackbackend.ui.theme.MedTrackBackendTheme

@Composable
fun InfoCardExtended(
    infoTopLeft: @Composable () -> Unit,
    infoTopRight: @Composable () -> Unit,
    infoBottomLeft: @Composable () -> Unit,
    infoBottomRight: @Composable() (() -> Unit?)? = null,
    containerColor: Color,
    iconColor: Color? = null,
    icon: Int? = null,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        modifier = Modifier
            .clickable(onClick = onClick)
            .clip(shape = RoundedCornerShape(8.dp))
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(containerColor)
            .padding(16.dp, 8.dp)
    ) {
        if (icon != null && iconColor != null) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "icon",
                tint = iconColor,
            )
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                infoTopLeft()
                Spacer(modifier = Modifier.height(4.dp))
                infoTopRight()
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                infoBottomLeft()
                Spacer(modifier = Modifier.height(4.dp))
                if (infoBottomRight != null) infoBottomRight()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoCardExtendedPreview() {
    MedTrackBackendTheme {
        InfoCardExtended(
            infoTopLeft = {
                Text(
                    text = "Medicine Name",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            infoTopRight = {
                Text(
                    text = "Medicine Name",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            infoBottomLeft = {
                Text(
                    text = "Medicine Name",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            infoBottomRight = {
                Text(
                    text = "Medicine Name",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            containerColor = MaterialTheme.colorScheme.surface,
            iconColor = MaterialTheme.colorScheme.primary
        ) { }
    }
}