package com.example.xgutierrezmusicapp.screens

sealed class Routes(val route: String) {
    object Home : Routes("home")

    object Detail : Routes("detail/{albumId}") {
        fun createRoute(albumId: String) = "detail/$albumId"
    }

    //object SongDetail : Routes("song_detail/{albumId}/{songTitle}") {
       // fun createRoute(albumId: String, songTitle: String) = "song_detail/$albumId/$songTitle"
    //}
}