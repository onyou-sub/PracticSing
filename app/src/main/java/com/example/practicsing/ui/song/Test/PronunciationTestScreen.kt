package com.example.practicsing.ui.song.Test

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.practicsing.viewmodel.AsrViewModel
import com.example.practicsing.R
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.ui.common.RoundedBackButton
import com.example.practicsing.ui.song.Test.tts.TTSProvider
@Composable
fun PronunciationTestScreen(
    navController: NavController,
    songId: String,
    viewModel: AsrViewModel = viewModel()
) {

    val chorusLines by viewModel.mainLyrics

    val chorusLineses = listOf(
        "달려가고 있어",
        "날 굳이 막지 말아",
        "손끝으로 세상을 두드려",
        "문이 열려 서로의 존재를 느껴"
    )




    LaunchedEffect(songId) {
        viewModel.loadMainLyrics(songId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(20.dp)
    ) {

        RoundedBackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp),
            onClick = { navController.popBackStack() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text(
                "Choose a line to listen:",
                style = MaterialTheme.typography.titleMedium,
                color = MainText
            )


            chorusLineses.forEach { line ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            TTSProvider.speak(line)
                        },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        Text(line)
                    }
                }
            }

        }
    }
}
