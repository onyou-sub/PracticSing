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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.practicsing.ui.common.PracticeSingModal
import com.example.practicsing.data.model.PracticeRecord
import com.example.practicsing.navigation.Screen

import com.google.gson.Gson
import android.net.Uri
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SongPlayerScreen(
    songId: String,
    navController: NavHostController,
    repo: SongRepositoryImpl = SongRepositoryImpl()
) {

    val context = LocalContext.current
    val viewModel: SongPracticeViewModel = viewModel()

    // 🎵 노래 정보 가져오기
    val song = remember { repo.getSongs().firstOrNull { it.id == songId } }
    if (song == null) return

    // raw 파일 ID 로드
    val resId = remember(key1 = song) {
        context.resources.getIdentifier(
            song.filename,
            "raw",
            context.packageName
        )
    }

    // 🎵 ExoPlayer
    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(
                MediaItem.fromUri("android.resource://${context.packageName}/$resId")
            )
            prepare()
        }
    }

    var selectedTab by remember { mutableStateOf("Main") }
    var isPlaying by remember { mutableStateOf(false) }
    var duration by rememberSaveable { mutableStateOf(0L) }
    var currentPosition by rememberSaveable { mutableStateOf(0L) }

    // 🎤 녹음 관련
    var isRecording by remember { mutableStateOf(false) }
    var recordedFilePath by remember { mutableStateOf<String?>(null) }
    var recordedDurationMs by remember { mutableStateOf<Long?>(null) }

    // 모달 상태
    var showModal by remember { mutableStateOf(false) }
    var modalTitle by remember { mutableStateOf("") }
    var modalSubtitle by remember { mutableStateOf<String?>(null) }
    var modalEmoji by remember { mutableStateOf("🎤") }
    var modalButtonText by remember { mutableStateOf("확인") }
    var modalOnClick: (() -> Unit)? by remember { mutableStateOf(null) }

    // 플레이어 상태 업데이트
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
        onDispose { player.release() }
    }

    fun togglePlay() {
        if (player.isPlaying) player.pause() else player.play()
    }

    fun showError(title: String, subtitle: String) {
        modalTitle = title
        modalSubtitle = subtitle
        modalEmoji = "⚠️"
        modalButtonText = "Check"
        modalOnClick = { showModal = false }
        showModal = true
    }

    // 🎶 UI
    Box(modifier = Modifier.fillMaxSize()) {

        RoundedBackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp),
            onClick = { navController.popBackStack() }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF430024), Color.Black)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SongHeader(song = song)

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
                            onSeek = { newPos -> player.seekTo(newPos) }
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        PlayPauseButton(
                            isPlaying = isPlaying,
                            onClick = { togglePlay() }
                        )

                        Spacer(Modifier.height(40.dp))

                        // 🎤 녹음 버튼
                        Button(
                            onClick = {
                                if (!isRecording) {
                                    viewModel.startRecording(context)
                                    isRecording = true
                                } else {
                                    val (path, recordedMs) = viewModel.stopRecording()
                                    recordedFilePath = path
                                    recordedDurationMs = recordedMs
                                    isRecording = false
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
                            Text(if (isRecording) "Finish Recording" else "Start Recording")
                        }

                        Spacer(modifier = Modifier.height(200.dp))

                        // ⭐ FINISH BUTTON ⭐
                        Button(
                            onClick = {

                                val durationSec = (recordedDurationMs ?: 0L) / 1000

                                if (recordedFilePath == null) {
                                    showError("No recordings.", "Record more than 10 seconds.")
                                    return@Button
                                }

                                if (durationSec < 10) {
                                    showError("녹음 시간이 너무 짧아요", "10초 이상 녹음해야 저장됩니다.")
                                    return@Button
                                }

                                val dateText = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
                                    .format(Date())

                                val record = PracticeRecord(
                                    id = "",
                                    songId = song.id,
                                    songTitle = song.title,
                                    artist = song.artist,
                                    albumImageUrl = song.imageUrl,
                                    recordingUrl = recordedFilePath!!,
                                    durationText = "${durationSec}초",
                                    practicedAtMillis = System.currentTimeMillis(),
                                    practicedDateText = dateText,
                                    aiScore = (75..95).random(),
                                    aiStrengthComment = "Stable tone with nice clarity.",
                                    aiImprovementComment = "Try improving breath control."
                                )

                                // ⭐ JSON 변환 + URI 인코딩 ⭐
                                val recordJson = Uri.encode(Gson().toJson(record))

                                navController.navigate(
                                    Screen.AiEvaluation.createRoute(recordJson)
                                )
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
                            Text("Finish", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                "Kor" -> LyricsScreen(song = song, "Kor")
                "Eng" -> LyricsScreen(song = song, "Eng")
            }
        }
    }

    PracticeSingModal(
        visible = showModal,
        emoji = modalEmoji,
        title = modalTitle,
        subtitle = modalSubtitle,
        buttonText = modalButtonText,
        onDismissRequest = { showModal = false },
        onButtonClick = { modalOnClick?.invoke() }
    )
}
