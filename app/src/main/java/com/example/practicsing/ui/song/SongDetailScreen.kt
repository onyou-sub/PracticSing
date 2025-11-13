// 네비게이션 엔트리

package com.example.practicsing.ui.song

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.practicsing.ui.common.SongDetailContent

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
