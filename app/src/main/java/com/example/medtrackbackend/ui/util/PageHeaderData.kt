package com.example.medtrackbackend.ui.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Today
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.medtrackbackend.R


enum class PageHeaderData(
    val title: String,
    val subtitle: String,
    val iconImage: ImageVector? = null,
    val iconPainter: Int? = null
) {
    HOME(
        title = "Hello there,",
        subtitle = "Here are your meds for the day.",
        iconPainter = R.drawable.home
    ),
    CABINET(
        title = "Medicine Cabinet",
        subtitle = "here are all your medications.",
        iconPainter = R.drawable.cabinet
    ),
    MEDICINE_DETAILS(
        title = "Medicine Details",
        subtitle = "here is a detailed look of your medicine.",
        iconPainter = R.drawable.capsule
    ),
    ADD_MEDICINE_DETAILS(
        title = "Add Medicine",
        subtitle = "please input the details of your new medicine.",
        iconImage = Icons.Outlined.Add
    ),
    EDIT_MEDICINE_DETAILS(
        title = "Edit Medicine",
        subtitle = "please make your changes for your medicine.",
        iconImage = Icons.Outlined.Edit
    ),
    ADD_PROGRAM(
        title = "Add Program",
        subtitle = "please input the details of your new program.",
        iconImage = Icons.Outlined.Add
    ),
    EDIT_PROGRAM(
        title = "Edit Program",
        subtitle = "please make your changes for your program.",
        iconImage = Icons.Outlined.Today
    )
}