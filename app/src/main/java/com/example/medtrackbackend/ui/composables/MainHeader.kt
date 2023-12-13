package com.example.medtrackbackend.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.medtrackbackend.ui.screens.home.HomeViewModel
import com.example.medtrackbackend.ui.util.PageHeaderData

@Composable
fun MainHeader(
    pageHeader: PageHeaderData,
    viewModel: HomeViewModel?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .requiredHeight(207.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .wrapContentHeight(align = Alignment.Top, unbounded = true)
            .padding(16.dp)
    ) {
        HeaderText(
            titleText = pageHeader.title,
            subtitleText = pageHeader.subtitle
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            if (pageHeader.iconPainter != null) {
                Image(
                    painter = painterResource(pageHeader.iconPainter),
                    contentDescription = "Home SVG Art",
                )
            }
            if (pageHeader == PageHeaderData.HOME)
                DateContainer(viewModel)
        }
    }
}