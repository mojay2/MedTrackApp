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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medtrackbackend.data.Medicine
//import com.example.medtrackbackend.data.model.Medication
import com.example.medtrackbackend.ui.util.PageHeaderData

@Composable
fun MedicineDetailsHeader(
    medication: Medicine,
    pageHeader: PageHeaderData,
    hideMedicineName: Boolean = false,
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .requiredHeight(136.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .wrapContentHeight(align = Alignment.Top, unbounded = true)
            .padding(16.dp)
    ) {
        BackArrowWithTitle(pageHeader){
            navController.navigate("medicinecabinet")
        }
        Spacer(modifier = Modifier.height(24.dp))
        HeaderContent(medication, pageHeader, hideMedicineName)
    }
}

@Composable
private fun BackArrowWithTitle(
    pageHeader: PageHeaderData,
    onBackClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            Icons.Filled.ArrowBack,
            contentDescription = "Arrow Back Icon",
            modifier = Modifier.clickable(onClick = onBackClick)
        )
        HeaderText(
            titleText = pageHeader.title,
            subtitleText = pageHeader.subtitle
        )
        Spacer(modifier = Modifier.width(24.dp))
    }
}

@Composable
private fun HeaderContent(
    medication: Medicine,
    pageHeader: PageHeaderData,
    hideMedicineName: Boolean
) {
    val iconModifier = Modifier
        .height(80.dp)
        .width(80.dp)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface, CircleShape)
                .padding(8.dp)
        ) {
            if (pageHeader.iconImage != null) {
                Icon(
                    imageVector = pageHeader.iconImage,
                    contentDescription = "Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = iconModifier
                )
            } else if (pageHeader.iconPainter != null) {
                Icon(
                    painter = painterResource(pageHeader.iconPainter),
                    contentDescription = "Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = iconModifier
                )
            }
        }
        if (!hideMedicineName && medication.id != -1 && medication.id != 999) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = medication.medicineName,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            MedicineTag(medication = medication, size = TagSize.LARGE)
        }
    }
}
