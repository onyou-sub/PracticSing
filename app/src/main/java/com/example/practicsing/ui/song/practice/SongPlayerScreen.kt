package com.example.practicsing.ui.song.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.graphics.Brush
import com.example.practicsing.data.repository.SongRepositoryImpl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.MediaItem
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.media3.common.Player
import com.example.practicsing.ui.common.RoundedBackButton
import com.example.practicsing.ui.song.practice.component.AudioPlayerBar
import com.example.practicsing.ui.song.practice.component.LyricTabSelector
import com.example.practicsing.ui.song.practice.component.LyricsScreen
import com.example.practicsing.ui.song.practice.component.PlayPauseButton
import com.example.practicsing.ui.song.practice.component.SongHeader
import kotlinx.coroutines.delay
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practicsing.ui.song.practice.component.SongPracticeViewModel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import com.example.practicsing.ui.song.practice.component.RecorderManager

@Composable
fun SongPlayerScreen(
    songId: String,
    navController: NavHostController,
    repo: SongRepositoryImpl = SongRepositoryImpl()
) {

    val context = LocalContext.current
    val viewModel: SongPracticeViewModel = viewModel()

    val song = remember {
        repo.getSongs().firstOrNull { it.id == songId }
    }
    if (song == null) {

        return
    }
    val resId = remember(key1 = song) {
        context.resources.getIdentifier(
            song.filename,
            "raw",
            context.packageName
        )
    }
    // 플레이어 생성
    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(
                MediaItem.fromUri(
                    "android.resource://${context.packageName}/$resId"
                )
            )
            prepare()
        }
    }

    var selectedTab by remember { mutableStateOf("Main") }
    var isPlaying by remember { mutableStateOf(false) }
    var duration by rememberSaveable { mutableStateOf(0L) }
    var currentPosition by rememberSaveable { mutableStateOf(0L) }
    var isRecording by remember { mutableStateOf(false) }
    LaunchedEffect(player) {
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY) {
                    duration = player.duration
                }
            }

            override fun onIsPlayingChanged(isPlayingNow: Boolean) {
                isPlaying = isPlayingNow
            }
        })

        while (true) {
            currentPosition = player.currentPosition
            delay(200)
        }
    }

    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlayingNow: Boolean) {
                isPlaying = isPlayingNow
            }
        }

        player.addListener(listener)

        onDispose {
            player.removeListener(listener)
            player.release()
        }
    }

    fun togglePlay() {
        if (player.isPlaying) player.pause() else player.play()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        RoundedBackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp),
            onClick = { navController.popBackStack() }
        )
    }
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


            song?.let {
                SongHeader(song = it)
            } ?: Text("Song Not Found", color = Color.White)

            Spacer(modifier = Modifier.height(20.dp))


            LyricTabSelector(
                selected = selectedTab,
                onSelect = { selectedTab = it }
            )

            Spacer(modifier = Modifier.height(20.dp))


            when (selectedTab) {

                "Main" -> {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        AudioPlayerBar(
                            duration = duration,
                            currentPosition = currentPosition,
                            onSeek = { newPos -> player.seekTo(newPos) })

                        Spacer(modifier = Modifier.height(20.dp))

                        PlayPauseButton(
                            isPlaying = isPlaying,
                            onClick = { togglePlay() }
                        )
                        Spacer(Modifier.height(40.dp))

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = {
                                if (!isRecording) {
                                    viewModel.startRecording(context)
                                    isRecording = true
                                } else {
                                    val filePath = viewModel.stopRecording()
                                    isRecording = false
                                    println("record save this path: $filePath")
                                }
                            },
                            modifier = Modifier
                                .height(50.dp)
                                .width(200.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF0088),
                                contentColor = Color.White
                            )
                        ) {
                            Text(if (isRecording) "finish" else "start recording")
                        }

                        Spacer(modifier = Modifier.height(200.dp))

                        Button(
                            onClick = {
                                navController.navigate("practice_success")
                            },
                            modifier = Modifier
                                .height(50.dp)
                                .width(200.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF0088),
                                contentColor = Color.White
                            )

                        ) {
                            Text(
                                "Finish",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }



                    }
                }

                "Kor" -> {
                    LyricsScreen(song = song)
                }

                "Eng" -> {
                    // TO DO //
                }
            }


        }

    }




}

