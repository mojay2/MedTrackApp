package com.example.medtrack.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medtrack.R
import com.example.medtrack.ui.theme.MedTrackTheme

@Composable
fun DefaultBanner(paint: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = paint),
            contentDescription = "Home SVG Art",
        )
        DateContainer()
    }
}

@Preview(showBackground = true, heightDp = 178)
@Composable
fun HomeBannerPreview() {
    MedTrackTheme {
        DefaultBanner(R.drawable.home)
    }
}