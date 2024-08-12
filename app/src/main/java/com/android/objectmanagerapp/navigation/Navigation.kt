package com.android.objectmanagerapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.android.objectmanagerapp.ui.list.ObjectListView


@Composable
fun AppNavigation(startDestination: String, navController: NavHostController) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.ObjectList.route) {
            ObjectListView(
                onAddObjectClick = { navController.navigate(Screen.AddObject.route) },
                onEditObjectClick = { objectId -> navController.navigate(Screen.EditObject.createRoute(objectId)) }
            )
        }
/*
        composable(Screen.AddObject.route) {
            AddEditObjectScreen(
                onObjectSaved = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EditObject.route,
            arguments = listOf(navArgument("objectId") { type = NavType.LongType })
        ) { backStackEntry ->
            val objectId = backStackEntry.arguments?.getLong("objectId") ?: 0
            AddEditObjectScreen(
                objectId = objectId,
                onObjectSaved = { navController.popBackStack() }
            )
        }*/
    }
}