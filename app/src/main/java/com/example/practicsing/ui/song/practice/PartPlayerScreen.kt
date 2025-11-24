package com.example.practicsing.ui.song.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavHostController
import com.example.practicsing.data.repository.SongRepositoryImpl
import com.example.practicsing.ui.common.RoundedBackButton


@Composable
fun PartPlayerScreen(
    songId: String,
    navController: NavHostController,
    repo: SongRepositoryImpl = SongRepositoryImpl()
){




    Box(
        modifier = Modifier.fillMaxSize()
    ) {


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF430024),
                            Color(0xFF000000)
                        )
                    )
                )
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Choose the part you want to practice",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(20.dp))






        }
        Box(modifier = Modifier.fillMaxSize()) {
            RoundedBackButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 16.dp),
                onClick = { navController.popBackStack() }
            )
        }
    }
}