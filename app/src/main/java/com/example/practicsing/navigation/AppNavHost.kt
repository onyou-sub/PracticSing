package com.example.practicsing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.practicsing.ui.*
import com.example.practicsing.ui.home.HomeScreen
import com.example.practicsing.ui.my.MyScreen
import com.example.practicsing.ui.song.SongScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen() }
        composable("song") { SongScreen() }
        composable("rank") { RankScreen() }
        composable("my") { MyScreen() }
    }
}
