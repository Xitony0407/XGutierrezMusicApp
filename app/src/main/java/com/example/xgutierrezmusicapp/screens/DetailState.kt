package com.example.xgutierrezmusicapp.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.xgutierrezmusicapp.models.Album
import com.example.xgutierrezmusicapp.models.Resource
import com.example.xgutierrezmusicapp.services.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

data class DetailUiState(
    val albumDetail: Resource<Album> = Resource.Loading(),
    val isPlaying: Boolean = false
)

class DetailViewModel(
    private val api: ApiService = ApiService,
    private val albumId: String
) {
    var state by mutableStateOf(DetailUiState())
        private set

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        // Inicia la carga del detalle inmediatamente al crearse el ViewModel
        fetchAlbumDetail(albumId)
    }

    // Llama a la API para obtener el detalle de un álbum por su ID
    private fun fetchAlbumDetail(id: String) {
        coroutineScope.launch {
            try {
                // 1. Iniciar carga
                state = state.copy(albumDetail = Resource.Loading())

                // 2. Llamada de red con el ID
                val result = api.musicApi.getAlbumDetail(id)

                // 3. Éxito: actualizar estado con los datos
                state = state.copy(albumDetail = Resource.Success(result))

            } catch (e: IOException) {
                // 4. Error de red/conexión
                state = state.copy(
                    albumDetail = Resource.Error(
                        message = "Error de conexión: ${e.localizedMessage}"
                    )
                )
            } catch (e: Exception) {
                // 5. Error general
                state = state.copy(
                    albumDetail = Resource.Error(
                        message = "Error inesperado: ${e.localizedMessage}"
                    )
                )
            }
        }
    }

    fun toggleMiniPlayer() {
        state = state.copy(isPlaying = !state.isPlaying)
    }
}