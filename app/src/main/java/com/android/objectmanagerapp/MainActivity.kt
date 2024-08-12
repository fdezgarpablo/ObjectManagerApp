package com.android.objectmanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.objectmanagerapp.navigation.AppNavigation
import com.android.objectmanagerapp.navigation.Screen
import com.android.objectmanagerapp.ui.theme.ObjectManagerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var navController : NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ObjectManagerAppTheme {
                    navController = rememberNavController()
                    AppNavigation(startDestination = Screen.ObjectList.route, navController = navController!!)

            }
        }
    }
}