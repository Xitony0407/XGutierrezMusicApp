package com.example.xgutierrezmusicapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.xgutierrezmusicapp.models.Resource
import com.example.xgutierrezmusicapp.ui.theme.BackgroundLight
import com.example.xgutierrezmusicapp.screens.MiniPlayer
import com.example.xgutierrezmusicapp.screens.DetailHeader
import com.example.xgutierrezmusicapp.screens.AboutAlbumCard
import com.example.xgutierrezmusicapp.screens.AlbumSongsList
import com.example.xgutierrezmusicapp.ui.theme.XGutierrezMusicAppTheme

@Composable
private fun rememberDetailViewModel(
    albumId: String,
    viewModel: DetailViewModel = remember(albumId) { DetailViewModel(albumId = albumId) }
): DetailViewModel = viewModel

@Composable
fun DetailScreen(navController: NavController, albumId: String) {
    val viewModel = rememberDetailViewModel(albumId = albumId)
    val uiState = viewModel.state

    val loadedAlbum = (uiState.albumDetail as? Resource.Success)?.data

    Scaffold(
        bottomBar = {
            loadedAlbum?.let { album ->
                MiniPlayer(
                    title = album.title ?: "Desconocido",
                    artist = album.artist ?: "Desconocido",
                    isPlaying = uiState.isPlaying,
                    onPlayPauseClicked = viewModel::toggleMiniPlayer
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundLight)
                .padding(paddingValues) // Aplica padding para no cubrir el MiniPlayer
        ) {

            when (val albumResource = uiState.albumDetail) {
                is Resource.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                        Text(text = "Cargando detalle...", modifier = Modifier.padding(top = 70.dp))
                    }
                }

                is Resource.Success -> {
                    albumResource.data?.let { album ->
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            // 1. HEADER
                            item {
                                DetailHeader(album = album, navController = navController)
                            }

                            // 2. CARD ABOUT
                            item {
                                AboutAlbumCard(album = album)
                            }

                            item {
                                ArtistChip(album = album) // Llama a la nueva función
                            }

                            // 3. LISTA DE CANCIONES
                            item {
                                // PASAMOS EL NAV CONTROLLER
                                //AlbumSongsList(album = album, navController = navController)
                                // AGREGAMOS
                                AlbumSongsList(album = album, navController = navController)
                            }
                        }

                    }
                }

                is Resource.Error -> {
                    Text(
                        text = "ERROR: ${albumResource.message} (ID: $albumId)",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    XGutierrezMusicAppTheme {
        DetailScreen(
            navController = rememberNavController(),
            albumId = "1" // ID de prueba
        )
    }
}