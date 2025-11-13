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
        startDestination = Screen.Home.route // 하단 탭의 시작은 Home
    ) {
        // 1. Home 탭
        composable(Screen.Home.route) { HomeScreen() }

        // 2. Song 탭
        composable(Screen.Song.route) { SongScreen() }

        // 3. Practice 탭
        composable(Screen.Practice.route) {
            // PracticeScreen에서는 네비게이션을 사용할 수 있도록 NavController를 전달
            PracticeScreen(navController = navController)
        }

        // 4. MyPage 탭
        composable(Screen.MyPage.route) { MyScreen() }

        // 5. DailyPractice 상세 경로 (탭 내에서 이동 가능)
        composable(Screen.DailyPractice.route) { backStackEntry ->
            // DailyPractice/{streakCount} 라우트에서 인수를 읽어옵니다.
            val streakCount = backStackEntry.arguments?.getString("streakCount")?.toIntOrNull() ?: 0
            DailyPracticeDetailScreen(streakCount = streakCount)
        }

        // TODO: SongDetail, SongPractice 등 다른 상세 화면을 여기에 추가
    }
}

@Composable
private fun DailyPracticeDetailScreen(streakCount: Int) {
    // 임시 화면
    Text(text = "Daily Practice Detail Screen: Day $streakCount")
}

// 임시 컴포넌트: SongScreen
@Composable
private fun SongScreen() {
    Text(text = "Song Screen Placeholder")
}

// 임시 컴포넌트: MyScreen
@Composable
private fun MyScreen() {
    Text(text = "My Page Screen Placeholder")
}

// 임시 컴포넌트: HomeScreen (필요한 경우)
@Composable
private fun HomeScreen() {
    Text(text = "Home Screen Placeholder")
}

// 임시 컴포넌트: PracticeScreen (필요한 경우)
@Composable
private fun PracticeScreen(navController: NavHostController) {
    Text(text = "Practice Screen Placeholder")
}