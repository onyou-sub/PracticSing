package com.example.practicsing.ui.song.Test

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practicsing.viewmodel.AsrViewModel
import com.example.practicsing.R

@Composable
fun PronunciationTestScreen(
    viewModel: AsrViewModel = viewModel()
) {
    var script by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("input sentences:", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = script,
            onValueChange = { script = it },
            modifier = Modifier.fillMaxWidth()
        )


        Button(
            onClick = {
                if (script.isNotBlank()) {
                    viewModel.speakKorean(script)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("listen the sentence")
        }


        Button(
            onClick = {
                if (script.isNotBlank()) {
                    viewModel.testPronunciationFromFile(
                        resId = R.raw.test_audio_16k,
                        script = script
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("WAV file")
        }


        Spacer(Modifier.height(20.dp))

        Text(
            "평가 결과:",
            style = MaterialTheme.typography.titleMedium
        )

        val result = viewModel.pronunciationResult

        if (result != null) {
            Text("sentences: ${result.recognized}")
            Text("score: ${result.score}")
        } else {
            Text("Not Yet.")
        }
    }
}