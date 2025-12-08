@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.practicsing.ui.my

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.practicsing.data.model.PracticeRecord
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Typography
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import kotlinx.coroutines.delay

@Composable
fun ArchivePlayerDialog(
    record: PracticeRecord,
    onDismiss: () -> Unit,
    onRetry: () -> Unit,
    onShowEvaluation: () -> Unit
) {
    val context = LocalContext.current
    
    // Initialize ExoPlayer
    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            if (record.recordingUrl.isNotEmpty()) {
                setMediaItem(MediaItem.fromUri(record.recordingUrl))
                prepare()
            }
        }
    }

    var isPlaying by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0L) }
    var duration by remember { mutableStateOf(0L) }
    
    // Manage Player Lifecycle
    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY) {
                    duration = player.duration
                } else if (state == Player.STATE_ENDED) {
                    isPlaying = false
                    player.seekTo(0)
                }
            }
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

    // Update progress bar
    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            currentPosition = player.currentPosition
            delay(200) // Update every 200ms
        }
    }

    // Format time helper
    fun formatTime(ms: Long): String {
        val totalSeconds = ms / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80000000))      // 반투명 블랙
            .clickable(
                onClick = onDismiss, 
                indication = null, 
                interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
            )
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp)
                .clickable(enabled = false) {}, // Prevent closing when clicking on the card
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF141414)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 앨범 이미지
                AsyncImage(
                    model = record.albumImageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = record.songTitle,
                    color = MainText,
                    style = Typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = record.recordedDate,
                    color = MainText,
                    style = Typography.bodySmall
                )

                Spacer(Modifier.height(24.dp))

                // 슬라이더 + 시간
                Slider(
                    value = if (duration > 0) currentPosition.toFloat() / duration else 0f,
                    onValueChange = { 
                        val newPos = (it * duration).toLong()
                        player.seekTo(newPos)
                        currentPosition = newPos
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = SliderDefaults.colors(
                        thumbColor = PinkAccent,
                        activeTrackColor = PinkAccent,
                        inactiveTrackColor = Color(0xFF444444)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formatTime(currentPosition), 
                        color = MainText, 
                        style = Typography.labelSmall
                    )
                    Text(
                        text = if (duration > 0) formatTime(duration) else "00:00", 
                        color = MainText,
                        style = Typography.labelSmall
                    )
                }

                Spacer(Modifier.height(24.dp))

                // 하단 아이콘들 : Retry / Play / 메뉴
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Replay,
                        contentDescription = "Retry",
                        tint = MainText,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { onRetry() }
                    )

                    Spacer(Modifier.width(40.dp))

                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(PinkAccent)
                            .clickable {
                                if (isPlaying) player.pause() else player.play()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                            contentDescription = "Play/Pause",
                            tint = MainText,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(Modifier.width(40.dp))

                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "AI Evaluation",
                        tint = MainText,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { onShowEvaluation() }
                    )
                }
            }
        }
    }
}
