// file: com/example/practicsing/navigation/AppNavHost.kt
package com.example.practicsing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
// TODO: 각 화면 Composable을 실제 경로에 맞게 임포트해야 합니다.
import com.example.practicsing.ui.splash.SplashScreen
import com.example.practicsing.ui.song.SongDetailScreen
import com.example.practicsing.ui.song.SongPracticeScreen
import com.example.practicsing.ui.auth.LoginScreen

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
            LoginScreen(navController = navController)
        }

        // 3. Main Screen (Bottom Navigation Container)
        composable(Screen.Main.route) {
            // 하단 탭 전체를 관리하는 BottomNavGraph를 호출합니다.
            BottomNavGraph(navController = navController)
        }

        // 4. Song Detail Screen (songId 인수를 받음)
        composable(
            route = Screen.SongDetail.route, // "song_detail/{songId}"
            arguments = listOf(navArgument("songId") { type = NavType.LongType })
        ) { backStackEntry ->
            val songId = backStackEntry.arguments?.getLong("songId") ?: 0L
            SongDetailScreen(songId = songId, navController = navController)
        }

        // 5. Song Practice Screen (songId 인수를 받음)
        composable(
            route = Screen.SongPractice.route, // "song_practice/{songId}"
            arguments = listOf(navArgument("songId") { type = NavType.LongType })
        ) { backStackEntry ->
            val songId = backStackEntry.arguments?.getLong("songId") ?: 0L
            SongPracticeScreen(songId = songId, navController = navController)
        }
    }
}