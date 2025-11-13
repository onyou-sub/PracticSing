// 네비게이션 엔트리

package com.example.practicsing.ui.song.detail

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun SongDetailScreen(
    songId: Long,
    navController: NavHostController
) {
    SongDetailContent(
        songId = songId,
        navController = navController
    )
}
