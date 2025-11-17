package com.example.practicsing.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.practicsing.ui.home.HomeScreen
import com.example.practicsing.ui.song.SongScreen
import com.example.practicsing.ui.my.MyScreen
import com.example.practicsing.ui.pract.PracticeScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route // 시작 화면을 Home으로 설정
    ) {
        // 1. Home 탭
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        // 2. Song 탭
        composable(Screen.Song.route) {
            SongScreen()
        }

        // 3. Practice 탭
        composable(Screen.Practice.route) {
            PracticeScreen(navController)  // Practice 화면
        }

        // 4. MyPage 탭
        composable(Screen.MyPage.route) {
            MyScreen()
        }
    }
}
@Composable
private fun DailyPracticeDetailScreen(streakCount: Int) {

    Text(text = "Daily Practice Detail Screen: Day $streakCount")
}

@Composable
private fun SongScreen() {
    Text(text = "Song Screen Placeholder")
}


@Composable
private fun MyScreen() {
    Text(text = "My Page Screen Placeholder")
}

@Composable
private fun HomeScreen() {
    Text(text = "Home Screen Placeholder")
}


@Composable
private fun PracticeScreen(navController: NavHostController) {
    Text(text = "Practice Screen Placeholder")
}