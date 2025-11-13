package com.example.practicsing.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Black,
    progressColor: Color = Color(0xFFFF0088)
) {

    val progress by animateFloatAsState(
        targetValue = currentStep.toFloat() / totalSteps,
        animationSpec = tween(durationMillis = 500)
    )

    Column(modifier = modifier) {
        CenterAlignedTopAppBar(
            title = { Text(title, color = Color.White) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = containerColor
            )
        )
        // Progress bar under the top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(Color.Gray.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .background(progressColor)
            )
        }
    }
}
