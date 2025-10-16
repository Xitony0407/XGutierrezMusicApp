package com.example.xgutierrezmusicapp.models

import java.io.Serializable

data class Album(
    val id: String,
    val title: String?,
    val artist: String?,
    val image: String?,
    val description: String? = null,
    val songs: List<String>? = null
) : Serializable