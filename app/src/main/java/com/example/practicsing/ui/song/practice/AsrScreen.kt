package com.example.practicsing.ui.song.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.Color
import com.example.practicsing.BuildConfig
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

import com.example.practicsing.viewmodel.AsrViewModel

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
fun CircleIconButton(
    icon: ImageVector,
    backgroundColor: Color = Color(0xFF303030),
    iconTint: Color = Color.White,
    size: Int = 60,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .background(backgroundColor, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size((size * 0.55).dp)
        )
    }
}

@Composable
fun AsrScreen(viewModel: AsrViewModel = viewModel(), currentLine: String ) {

        PronunciationPracticeUI(
            currentLine = currentLine,
            viewModel = viewModel
        )
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



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {


                CircleIconButton(
                    icon = Icons.Default.VolumeUp,
                    backgroundColor = Color(0xFF505050),
                    onClick = { viewModel.speakKorean(currentLine) }
                )


                CircleIconButton(
                    icon = if (viewModel.isRecording) Icons.Default.Stop else Icons.Default.Mic,
                    backgroundColor = if (viewModel.isRecording) Color(0xFFFF4444) else Color(0xFF505050),
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
