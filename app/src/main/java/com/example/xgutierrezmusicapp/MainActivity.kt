package com.example.xgutierrezmusicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.xgutierrezmusicapp.screens.DetailScreen
import com.example.xgutierrezmusicapp.screens.HomeScreen
import com.example.xgutierrezmusicapp.screens.Routes
import com.example.xgutierrezmusicapp.ui.theme.XGutierrezMusicAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XGutierrezMusicAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.Home.route
                ) {
                    // 1. Ruta de Home
                    composable(Routes.Home.route) {
                        HomeScreen(navController = navController,)
                    }

                    // 2. Ruta de Detail, requiere el argumento 'albumId'
                    composable(
                        route = Routes.Detail.route,
                        arguments = listOf(
                            navArgument("albumId") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val albumId = backStackEntry.arguments?.getString("albumId")

                        if (albumId.isNullOrEmpty()) {
                            navController.navigate(Routes.Home.route) {
                                popUpTo(Routes.Home.route) { inclusive = true } // Limpia la pila para evitar bucles
                            }
                        } else {
                            DetailScreen(navController = navController, albumId = albumId)
                        }
                    }
            }
        }
    }
    }
}