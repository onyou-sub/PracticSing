package com.example.practicsing.ui.song.practice

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.Color

import com.example.practicsing.viewmodel.AsrViewModel


@Composable
fun AsrScreen(viewModel: AsrViewModel = viewModel(), currentLine: String ) {

        PronunciationPracticeUI(
            currentLine = currentLine,
            viewModel = viewModel
        )
    }


