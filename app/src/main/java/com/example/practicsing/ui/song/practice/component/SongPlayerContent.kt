package com.example.practicsing.ui.song.practice.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.*
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import com.example.practicsing.main.theme.PinkAccent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import com.example.practicsing.data.model.Song
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Mic
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
fun formatTime(millis: Long): String {
    val totalSeconds = millis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}


@Composable
fun AudioPlayerBar(
    duration: Long,
    currentPosition: Long,
    onSeek: (Long) -> Unit
) {
    val progress = if (duration > 0) {
        currentPosition.toFloat() / duration.toFloat()
    } else 0f

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color.DarkGray)
                .pointerInput(duration) {
                    detectTapGestures { offset ->
                        val width = size.width.toFloat()
                        val fraction = (offset.x / width).coerceIn(0f, 1f)
                        onSeek((duration * fraction).toLong())
                    }
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(PinkAccent)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(0.85f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(formatTime(currentPosition), color = Color.White)
            Text(formatTime(duration), color = Color.White)
        }
    }
}




@Composable
fun PlayPauseButton(
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color(0xFFFF0088))
    ) {
        Icon(
            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            tint = Color.White,
            contentDescription = null
        )
    }
}

@Composable
fun RecordButton(
    isRecording: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(if (isRecording) Color.Red else Color.DarkGray)
    ) {
        Icon(
            imageVector = Icons.Default.Mic,
            contentDescription = null,
            tint = Color.White
        )
    }
}


@Composable
fun LyricTabSelector(
    selected: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
            .background(Color(0xFF2A2A2A), RoundedCornerShape(30.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        listOf("Main", "Kor", "Eng").forEach { tab ->
            val isSelected = tab == selected

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(34.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) Color.White else Color.Transparent)
                    .clickable { onSelect(tab) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tab,
                    color = if (isSelected) Color.Black else Color.White
                )
            }
        }
    }
}

@Composable
fun SongHeader(song: Song) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AsyncImage(
            model = song.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(song.title, color = Color.White, style = MaterialTheme.typography.titleLarge)
            Text(song.artist, color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

data class LyricsLine(
    val time: Long,
    val text: String
)

@Composable
fun LyricsScreen(song: Song) {

    val db = FirebaseFirestore.getInstance()

    var lyrics by remember { mutableStateOf("가사를 불러오는 중...") }
    var title by remember { mutableStateOf("") }

    // Firestore 불러오기
    LaunchedEffect(song) {
        db.collection("lyrics")
            .document(song.filename)
            .get()
            .addOnSuccessListener { doc ->
                lyrics = doc.getString("lyrics") ?: "가사를 찾을 수 없습니다."
                title = doc.getString("title") ?: song.title
            }
            .addOnFailureListener {
                lyrics = "가사 불러오기 실패"
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {


        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 10.dp)
        )


        Text(
            text = lyrics,
            color = Color(0xCCFFFFFF),
            fontSize = 18.sp
        )
    }
}
