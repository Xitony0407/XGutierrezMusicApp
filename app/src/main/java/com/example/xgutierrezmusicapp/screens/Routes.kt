package com.example.xgutierrezmusicapp.screens

sealed class Routes(val route: String) {
    object Home : Routes("home")

    object Detail : Routes("detail/{albumId}") {
        fun createRoute(albumId: String) = "detail/$albumId"
    }
}