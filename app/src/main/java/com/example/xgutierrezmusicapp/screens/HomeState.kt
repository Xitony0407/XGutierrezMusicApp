package com.example.xgutierrezmusicapp.screens


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xgutierrezmusicapp.models.Album
import com.example.xgutierrezmusicapp.models.Resource
import com.example.xgutierrezmusicapp.services.ApiService
import kotlinx.coroutines.launch
import java.io.IOException

data class HomeUiState(
    val albums: Resource<List<Album>> = Resource.Loading(emptyList()),
    val currentMiniPlayerAlbum: Album? = null,
    val isPlaying: Boolean = false
)

class HomeViewModel(
    private val api: ApiService = ApiService,
    shouldFetchData: Boolean = true
) {
    var state by mutableStateOf(HomeUiState())

    private val coroutineScope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO)


    init {
        if (shouldFetchData) {
            fetchAlbums()
        }
    }

    fun setMiniPlayerAlbum(album: Album) {
        state = state.copy(
            currentMiniPlayerAlbum = album,
            isPlaying = true
        )
    }

    // Llama a la API para obtener la lista de álbumes
    fun fetchAlbums() {
        coroutineScope.launch {
            try {
                // 1. Iniciar carga
                state = state.copy(albums = Resource.Loading())

                // 2. Llamada de red
                val result = api.musicApi.getAlbums()

                // 3. Éxito: actualizar estado con los datos
                state = state.copy(albums = Resource.Success(result))

            } catch (e: IOException) {
                // 4. Error de red/conexión
                state = state.copy(
                    albums = Resource.Error(
                        message = "Error de conexión: ${e.localizedMessage}"
                    )
                )
            } catch (e: Exception) {
                // 5. Error general
                state = state.copy(
                    albums = Resource.Error(
                        message = "Error inesperado: ${e.localizedMessage}"
                    )
                )
            }
        }
    }

    // Función para manejar el estado de play/pause del mini reproductor
    fun toggleMiniPlayer() {
        state = state.copy(isPlaying = !state.isPlaying)
    }
}