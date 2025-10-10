package com.example.xgutierrezmusicapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.offset
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.xgutierrezmusicapp.models.Album
import com.example.xgutierrezmusicapp.ui.theme.CardWhite
import com.example.xgutierrezmusicapp.ui.theme.HeaderPurpleEnd
import com.example.xgutierrezmusicapp.ui.theme.HeaderPurpleStart
import com.example.xgutierrezmusicapp.ui.theme.MiniPlayerDark
import com.example.xgutierrezmusicapp.ui.theme.Purple40

@Composable
fun HomeHeader() {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(HeaderPurpleStart, HeaderPurpleEnd),
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(gradientBrush)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .padding(bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Handle Menu Click */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menú",
                    tint = Color.White
                )
            }
            IconButton(onClick = { /* Handle Search Click */ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Buscar",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¡Buen día!",
            fontSize = 18.sp,
            color = Color.White
        )
        Text(
            text = "Ximena Perez",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AlbumCard(album: Album, navController: NavController) {
    Card(
        onClick = {
            // Navegación al detalle
            navController.navigate(Routes.Detail.createRoute(album.id))
        },
        modifier = Modifier
            .padding(end = 12.dp)
            .size(width = 160.dp, height = 200.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Imagen de Fondo (Coil)
            AsyncImage(
                model = album.cover,
                contentDescription = "Cover de ${album.title}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Scrim/Overlay Oscuro
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                            startY = 0f
                        )
                    )
            )

            // Texto (Título y Artista)
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text(
                    text = album.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1
                )
                Text(
                    text = album.artist,
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    maxLines = 1
                )
            }

            // Botón de Play Circular
            IconButton(
                onClick = { /* Acción de Play */ },
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = (-10).dp, y = (-10).dp)
                    .background(Color.White, RoundedCornerShape(50))
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Reproducir",
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun AlbumsLazyRow(albums: List<Album>, navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth()) {

        // Header "Albums" y "See more"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Albums",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "See more",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // LazyRow Horizontal
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(albums) { album ->
                AlbumCard(album = album, navController = navController)
            }
        }
    }
}


@Composable
fun RecentlyPlayedCard(album: Album,
                       navController: NavController,
                       onAlbumSelected: (Album) -> Unit) {
    Card(
        onClick = {
            onAlbumSelected(album)
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
            // Imagen Miniatura (Coil)
            AsyncImage(
                model = album.cover,
                contentDescription = "Cover de ${album.title}",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Título, Artista y Subtítulo
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = album.title,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    maxLines = 1
                )
                Text(
                    text = "${album.artist} • Popular Song",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    maxLines = 1
                )
            }

            // Icono de Menú
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
fun RecentlyPlayedLazyColumn(
    albums: List<Album>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onAlbumSelected: (Album) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {

        // Header "Recently Played" y "See more"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recently Played",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "See more",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // LazyColumn Vertical
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(albums) { album ->
                RecentlyPlayedCard(album = album, navController = navController, onAlbumSelected = onAlbumSelected)
            }
        }
    }
}


@Composable
fun MiniPlayer(
    title: String,
    artist: String,
    isPlaying: Boolean,
    onPlayPauseClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MiniPlayerDark)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Mini imagen de álbum (Placeholder)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Purple40, RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Título y artista
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1
                )
                Text(
                    text = artist,
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    maxLines = 1
                )
            }

            // Botón Play/Pause
            IconButton(
                onClick = onPlayPauseClicked,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, RoundedCornerShape(50))
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pausar" else "Reproducir",
                    tint = Color.Black
                )
            }
        }
    }
}