package com.example.practicsing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.practicsing.ui.home.HomeScreen
import com.example.practicsing.ui.song.SongScreen
import com.example.practicsing.ui.my.MyScreen
import com.example.practicsing.ui.pract.PracticeScreen

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
            MyScreen()
        }
    }
}
