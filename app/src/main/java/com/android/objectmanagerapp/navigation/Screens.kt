package com.android.objectmanagerapp.navigation

sealed class Screen(val route: String) {
    data object ObjectList : Screen("objectList")
    data object DetailObject : Screen("detailObject?objectId={objectId}&mode={mode}") {
        fun createRoute(objectId: String? = null, mode: String): String {
            return "detailObject?objectId=${objectId ?: ""}&mode=${mode}"
        }
    }
}