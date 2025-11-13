package com.example.practicsing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.practicsing.ui.home.HomeScreen
import com.example.practicsing.ui.song.SongScreen
//import com.example.practicsing.ui.rank.RankScreen // RankScreen Import
import com.example.practicsing.ui.my.MyScreen



@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route // 하단 탭의 시작은 Home
    ) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Song.route) { SongScreen() }
        composable(Screen.Rank.route) { RankScreen() }
        composable(Screen.My.route) { MyScreen() }
    }
}

@Composable
fun RankScreen() {
    TODO("Not yet implemented")
}