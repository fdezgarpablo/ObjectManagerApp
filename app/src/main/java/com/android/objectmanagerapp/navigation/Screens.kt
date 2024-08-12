package com.android.objectmanagerapp.navigation

sealed class Screen(val route: String) {
    data object ObjectList : Screen("objectList")
    data object AddObject : Screen("addObject")
    data object EditObject : Screen("editObject/{objectId}") {
        fun createRoute(objectId: Int) = "editObject/$objectId"
    }
}