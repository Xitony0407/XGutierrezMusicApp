package com.example.xgutierrezmusicapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.xgutierrezmusicapp.models.Album
import com.example.xgutierrezmusicapp.models.Resource
import com.example.xgutierrezmusicapp.ui.theme.BackgroundLight
import com.example.xgutierrezmusicapp.ui.theme.XGutierrezMusicAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel



@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel()
) {
    val uiState = homeViewModel.state
    val scrollState = rememberScrollState()
    Scaffold(
        bottomBar = {
            uiState.currentMiniPlayerAlbum?.let { album ->
                MiniPlayer(
                    title = album.title ?: "Desconocido",
                    artist = album.artist ?: "Desconocido",
                    isPlaying = uiState.isPlaying,
                    onPlayPauseClicked = homeViewModel::toggleMiniPlayer
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundLight)
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            HomeHeader()

            when (val albumsResource = uiState.albums) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is Resource.Success -> {
                    albumsResource.data?.let { albums ->
                        // Carrusel de Álbumes (LazyRow)
                        AlbumsLazyRow(albums = albums,
                            navController = navController,
                            onAlbumSelected = homeViewModel::setMiniPlayerAlbum)

                        // Lista de "Recently Played"
                        RecentlyPlayedList(
                            albums = albums.take(5),
                            navController = navController,
                            onAlbumSelected = homeViewModel::setMiniPlayerAlbum
                        )
                    }
                }
                is Resource.Error -> {
                    Text(
                        text = "ERROR: ${albumsResource.message}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}


private val dummyAlbum = Album(
    id = "1",
    title = "Tales of Ithiria",
    artist = "Haggard",
    image = "https://example.com/dummy.jpg", // URL de imagen
    description = "Descripción ficticia para el preview."
)
private val dummyViewModel = HomeViewModel(shouldFetchData = false).apply {
    // Configura el estado para que sea Success de forma inmediata en el Preview
    state = state.copy(albums = Resource.Success(listOf(dummyAlbum, dummyAlbum, dummyAlbum)))
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    XGutierrezMusicAppTheme {
        HomeScreen(
            navController = rememberNavController(),
            homeViewModel = dummyViewModel //
        )
    }
}