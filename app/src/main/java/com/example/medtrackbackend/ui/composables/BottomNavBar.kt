package com.example.medtrackbackend.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.medtrackbackend.ui.util.Routes

@Composable
fun BottomNavBar(navController: NavController) {
    val cabinetRoutes = setOf(
        Routes.CABINET,
        Routes.MEDICINE_DETAILS,
        Routes.ADD_EDIT_MEDICINE_DETAILS,
        Routes.ADD_EDIT_PROGRAM
    )

    val currentRoute =
        navController.currentBackStackEntry?.destination?.route

    val homeIconTint =
        if (currentRoute == Routes.HOME || currentRoute == "Home")
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.secondary

    val cabinetIconTint =
        if (currentRoute != Routes.HOME && currentRoute != "Home" )
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.secondary

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.height(56.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 12.dp)
        ) {
            Icon(
                Icons.Outlined.Home,
                contentDescription = "Home Nav",
                tint = homeIconTint,
                modifier = Modifier
                    .size(32.dp)
                    .weight(1f)
                    .clickable {
                        navController.navigate(Routes.HOME)
                    }
            )
            Icon(
                Icons.Outlined.Inventory2,
                contentDescription = "Cabinet Nav",
                tint = cabinetIconTint,
                modifier = Modifier
                    .size(32.dp)
                    .weight(1f)
                    .clickable {
                        navController.navigate(Routes.CABINET)
                    }
            )
        }
    }
}