package com.example.medtrackbackend.ui.screens.home

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.medtrackbackend.data.DateUtil
import com.example.medtrackbackend.data.Medicine
import com.example.medtrackbackend.ui.composables.BottomNavBar
import com.example.medtrackbackend.ui.composables.ConfirmModal
import com.example.medtrackbackend.ui.composables.DatePickerDialog
import com.example.medtrackbackend.ui.composables.HomeMedicationList
import com.example.medtrackbackend.ui.composables.IntakeTimeCard
import com.example.medtrackbackend.ui.composables.MainHeader
import com.example.medtrackbackend.ui.composables.MedicationList
import com.example.medtrackbackend.ui.composables.MedicineCard
import com.example.medtrackbackend.ui.util.LocalCustomColorsPalette
import com.example.medtrackbackend.ui.util.PageHeaderData

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController
){
    val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
    val homeState = homeViewModel.state
    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }
    ) {innerPadding ->
        Surface(
            color = LocalCustomColorsPalette.current.surfaceContainer,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column() {
                Box(
                    modifier = Modifier
                        .padding(0.dp)
                ) {
                    MainHeader(
                        pageHeader = PageHeaderData.HOME,
                        viewModel = homeViewModel
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(top = 56.dp, start = 16.dp, end = 16.dp, bottom = 72.dp)
                ) {
                    HomeMedicationList(
                        medications = homeState.intakeTimes,
                        navController,
                        viewModel = homeViewModel
                    )
                }
            }
        }
    }
}
