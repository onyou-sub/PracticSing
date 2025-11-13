package com.example.practicsing.ui.song.practice

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun SongPracticeScreen(
    songId: Long,
    navController: NavHostController
) {
    // 단순히 Content만 호출 (Navigation Entry 역할)
    SongPracticeContent(
        songId = songId,
        navController = navController
    )
}
