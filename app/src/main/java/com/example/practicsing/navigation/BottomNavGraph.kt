package com.example.practicsing.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation // navigation import 문제 해결
import com.example.practicsing.ui.home.HomeScreen
import com.example.practicsing.ui.song.SongScreen
import com.example.practicsing.ui.my.MyScreen
import com.example.practicsing.ui.pract.PracticeScreen
import com.example.practicsing.ui.search.SearchScreen
import com.example.practicsing.ui.song.detail.SongDetailScreen

import androidx.navigation.NavType
import androidx.navigation.navArgument

fun NavGraphBuilder.bottomNavGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.Home.route,
        route = Screen.Main.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.Song.route) {
            SongScreen(navController = navController)
        }

        composable(Screen.Practice.route) {
            PracticeScreen(navController = navController)
        }

        composable(Screen.MyPage.route) {
            MyScreen(navController = navController)
        }

        composable(Screen.Search.route) {
            SearchScreen(navController = navController)
        }

        // 🎵 곡 상세 화면 (아래에서 설명)
        composable(
            route = Screen.SongDetail.route,
            arguments = listOf(
                navArgument("songId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("songId") ?: return@composable
            SongDetailScreen(
                navController = navController,
                songId = songId
            )
        }
    }
}