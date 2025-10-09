package com.example.xgutierrezmusicapp.services

import com.example.xgutierrezmusicapp.models.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicApi {

    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @GET("albums/{id}")
    suspend fun getAlbumDetail(@Path("id") albumId: String): Album
}

object ApiService {
    val musicApi: MusicApi by lazy {
        RetrofitClient.retrofit.create(MusicApi::class.java)
    }
}