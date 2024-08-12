package com.android.objectmanagerapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable


@Composable
fun AppNavigation(startDestination: String, navController: NavHostController) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = "") {
        }
    }
}