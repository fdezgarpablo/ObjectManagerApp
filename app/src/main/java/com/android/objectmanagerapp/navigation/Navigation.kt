package com.android.objectmanagerapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.objectmanagerapp.data.model.ModeType
import com.android.objectmanagerapp.ui.create.ObjectDetailView
import com.android.objectmanagerapp.ui.list.ObjectListView


@Composable
fun AppNavigation(startDestination: String, navController: NavHostController) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.ObjectList.route) {
            ObjectListView(
                onAddObjectClick = {
                    navController.navigate(
                        Screen.DetailObject.createRoute(
                            objectId = null,
                            mode = ModeType.CREATE.name
                        )
                    )
                },
                onEditObjectClick = { id ->
                    navController.navigate(
                        Screen.DetailObject.createRoute(
                            objectId = id,
                            mode = ModeType.EDIT.name
                        )
                    )
                },
                onCardClick = { id ->
                    navController.navigate(
                        Screen.DetailObject.createRoute(
                            objectId = id,
                            mode = ModeType.READ.name
                        )
                    )
                }
            )
        }

        composable(enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500),
            )
        }, exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500),
            )
        },
            route = Screen.DetailObject.route,
            arguments = listOf(
                navArgument("objectId") { nullable = true },
                navArgument("mode") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val objectId = backStackEntry.arguments?.getString("objectId")
            val mode = backStackEntry.arguments?.getString("mode")

            ObjectDetailView(
                objectId = objectId,
                mode = mode!!,
                onCancelClick = { navController.popBackStack() }
            )
        }
    }
}