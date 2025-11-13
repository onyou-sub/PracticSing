package com.example.practicsing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType

// UI 및 기타 임포트 (필요 없는 임포트 제거)
// import androidx.compose.ui.Modifier // 아래 코드로 인해 필요 없어짐

// 프로젝트 내부 임포트 (화면 컴포저블은 실제 파일에 정의되어 있다고 가정)
// ⚠️ 주의: 이 경로에 실제 컴포넌트가 존재해야 합니다!
import com.example.practicsing.ui.splash.SplashScreen
import com.example.practicsing.ui.auth.LoginScreen
import com.example.practicsing.ui.song.detail.SongDetailScreen
import com.example.practicsing.ui.song.practice.SongPracticeScreen
import com.example.practicsing.ui.search.SearchScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        // 1. Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        // 2. Login Screen
        composable(Screen.Login.route) {
            // ⭐ Placeholder 대신 실제 컴포넌트 사용
            LoginScreen(navController = navController)
        }

        // 3. Main Screen (Bottom Navigation Container)
        composable(Screen.Main.route) {
            BottomNavGraph(navController = navController)
        }

        // 4. Search Overlay Screen (최상위 모달 경로)
        composable(Screen.Search.route) {
            SearchScreen(navController = navController)
        }

        // 5. Song Detail Screen (songId 인수를 받음)
        composable(
            route = Screen.SongDetail.route, // "song_detail/{songId}"
            arguments = listOf(navArgument("songId") { type = NavType.LongType })
        ) { backStackEntry ->
            val songId = backStackEntry.arguments?.getLong("songId") ?: 0L
            // ⭐ Placeholder 대신 실제 컴포넌트 사용. 파라미터 일치 필수!
            SongDetailScreen(songId = songId, navController = navController)
        }

        // 6. Song Practice Screen (songId 인수를 받음)
        composable(
            route = Screen.SongPractice.route, // "song_practice/{songId}"
            arguments = listOf(navArgument("songId") { type = NavType.LongType })
        ) { backStackEntry ->
            val songId = backStackEntry.arguments?.getLong("songId") ?: 0L
            // ⭐ Placeholder 대신 실제 컴포넌트 사용. 파라미터 일치 필수!
            SongPracticeScreen(songId = songId, navController = navController)
        }
    }
}