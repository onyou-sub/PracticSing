package com.example.practicsing.ui.song.practice

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.practicsing.ui.common.RoundedBackButton

@Composable
fun SongPracticeScreen(
    songId: String,
    navController: NavHostController
) {
    // 단순히 Content만 호출 (Navigation Entry 역할)
    SongPracticeContent(
        songId = songId,
        navController = navController
    )


}
