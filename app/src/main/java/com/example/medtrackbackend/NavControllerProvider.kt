package com.example.medtrackbackend

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

class NavControllerProvider {
    companion object {
        @Volatile
        private var instance: NavController? = null

        fun provideNavController(): NavController {
            return requireNotNull(instance) { "NavController not initialized" }
        }

        fun initialize(navController: NavController) {
            instance = navController
        }
    }
}