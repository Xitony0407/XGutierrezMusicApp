package com.example.xgutierrezmusicapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.xgutierrezmusicapp.models.Album
// Reutilizamos los colores del Header
import com.example.xgutierrezmusicapp.ui.theme.HeaderPurpleStart
import com.example.xgutierrezmusicapp.ui.theme.HeaderPurpleEnd
import com.example.xgutierrezmusicapp.ui.theme.MiniPlayerDark
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import com.example.xgutierrezmusicapp.ui.theme.CardWhite
import androidx.compose.material.icons.filled.MoreVert


@Composable
fun DetailHeader(album: Album, navController: NavController) {
    // Gradiente morado que actuará como scrim (overlay)
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color.Transparent, HeaderPurpleStart.copy(alpha = 0.2f), HeaderPurpleEnd.copy(alpha = 0.2f)),
        startY = 0f,
        endY = 400f
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        AsyncImage(
            model = album.image,
            contentDescription = "Cover de ${album.title}",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            // Establecer un color de fondo temporal mientras carga
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
        )

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(top = 40.dp, start = 16.dp) // Espacio para la barra de estado
                .align(Alignment.TopStart)
                .background(Color.Black.copy(alpha = 0.3f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Text(
                text = album.title ?: "Álbum Desconocido",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                maxLines = 2
            )
            Text(
                text = album.artist ?: "Artista Desconocido",
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                // Botón Play
                IconButton(
                    onClick = { /* Handle Play */ },
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color.White, CircleShape)
                ) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = "Play", tint = Color.Black, modifier = Modifier.size(32.dp))
                }


                IconButton(
                    onClick = {  },
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color.White, CircleShape)
                ) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = "Play", tint = Color.Black, modifier = Modifier.size(32.dp))
                }
            }
        }
    }
}


@Composable
fun AboutAlbumCard(album: Album) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "About this album",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = album.description ?: "No hay una descripción disponible para este álbum.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )

        }
    }
}

@Composable
fun ArtistChip(album: Album) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp) //
            .wrapContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = "Artist: ${album.artist ?: "Desconocido"}",
                color = MaterialTheme.colorScheme.primary, // Color del texto morado
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun SongItemCard(songTitle: String,
                 artistName: String,
                 coverUrl: String?,
                 //albumId: String,
                 //navController: NavController
){
    Card(
        onClick = {
            //navController.navigate(Routes.SongDetail.createRoute(albumId, songTitle))
            // AGREGAMOS
            println("Reproduciendo: $songTitle")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = coverUrl,
                contentDescription = "Cover de canción",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = songTitle,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    maxLines = 1
                )
                Text(
                    text = artistName,
                    color = Color.Gray,
                    fontSize = 13.sp,
                    maxLines = 1
                )
            }

            IconButton(onClick = { /* Handle Menu Click */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menú",
                    tint = Color.Gray
                )
            }
        }
    }
}


@Composable
fun AlbumSongsList(album: Album,
                   navController: NavController) {
    val songsToDisplay = if (album.songs.isNullOrEmpty()) {
        List(10) { index -> "${album.title} • Track ${index + 1}" }
    } else {
        album.songs
    }

    songsToDisplay.forEach { songTitle ->
        SongItemCard(
            songTitle = songTitle,
            artistName = album.artist ?: "Artista Desconocido",
            coverUrl = album.image,
            //albumId = album.id ?: "0",
            //navController = navController
        )
    }

    Spacer(modifier = Modifier.height(20.dp))
}