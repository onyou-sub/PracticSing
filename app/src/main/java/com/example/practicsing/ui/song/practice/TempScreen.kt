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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.practicsing.data.repository.SongRepositoryImpl
import com.example.practicsing.ui.common.RoundedBackButton
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.*
import com.example.practicsing.data.model.Song
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.platform.LocalContext
import android.speech.tts.TextToSpeech
import java.util.Locale
import com.example.practicsing.viewmodel.AsrViewModel
import androidx.compose.ui.draw.clip
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practicsing.BuildConfig
@Composable
fun PinkButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF0088),
            contentColor = Color.White
        )
    ) {
        Text(text)
    }
}


@Composable
fun TempScreen(
    songId: String,
    navController: NavHostController,
    repo: SongRepositoryImpl = SongRepositoryImpl(),
    viewModel: AsrViewModel = viewModel()
) {

    val song = remember { repo.getSongs().firstOrNull { it.id == songId } }
    if (song == null) return

    val db = FirebaseFirestore.getInstance()

    var lyricsLines by remember { mutableStateOf<List<String>>(emptyList()) }
    var currentIndex by remember { mutableStateOf(0) }
    var title by remember { mutableStateOf(song.title) }


    LaunchedEffect(song) {
        db.collection("lyrics")
            .document(song.filename)
            .get()
            .addOnSuccessListener { doc ->
                val fullLyrics = doc.getString("lyrics") ?: ""
                title = doc.getString("title") ?: song.title
                lyricsLines = fullLyrics.split("\n").filter { it.isNotBlank() }
                currentIndex = 0
            }
            .addOnFailureListener {
                lyricsLines = listOf("가사를 불러올 수 없습니다.")
            }
    }


    val currentLine = if (lyricsLines.isNotEmpty()) lyricsLines[currentIndex] else "loading.."

    Box(modifier = Modifier.fillMaxSize()) {

        // Background
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

            Text("Practice Korean lyrics",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(20.dp))


            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(40.dp))


            Text(
                text = currentLine,
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(80.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                PinkButton(
                    text = "evaluate",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        navController.navigate("asr_screen/${currentLine}")
                    }
                )

                PinkButton(
                    text = "next",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (currentIndex < lyricsLines.size - 1) {
                            currentIndex++
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(200.dp))

            PinkButton(
                text = "Finish",
                modifier = Modifier.width(200.dp),
                onClick = { /* TODO */ }
            )
        }

        RoundedBackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
            onClick = { navController.popBackStack() }
        )
    }
}


@Composable
fun PronunciationPracticeUI(
    currentLine: String,
    viewModel: AsrViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = currentLine,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 30.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            PinkButton(
                text = "listen",
                modifier = Modifier.weight(1f),
                onClick = { viewModel.speakKorean(currentLine) }
            )

            PinkButton(
                text = if (viewModel.isRecording) "stop " else "speak",
                modifier = Modifier.weight(1f),
                onClick = {
                    if (!viewModel.isRecording)
                        viewModel.startRecording()
                    else
                        viewModel.stopRecordingPronunciation(
                            accessKey = BuildConfig.ETRI_KEY,
                            script = currentLine
                        )
                }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))


        viewModel.pronunciationResult?.let { result ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0x33000000))
                    .padding(20.dp)
            ) {
                Column {
                    Text("result", color = Color.White)
                    Spacer(Modifier.height(12.dp))
                    Text("sentence: ${result.recognized}", color = Color.White)
                    Text("accuracy: ${result.score}%", color = Color.White)
                }
            }
        }
    }
}
