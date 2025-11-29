package com.example.practicsing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.practicsing.ui.auth.LoginScreen2
import com.example.practicsing.ui.auth.RegisterScreen
import com.example.practicsing.ui.splash.SplashScreen
import com.example.practicsing.ui.search.SearchScreen
import com.example.practicsing.ui.song.detail.SongDetailScreen
import com.example.practicsing.ui.song.practice.SongPracticeScreen
import com.example.practicsing.ui.song.practice.SongPlayerScreen
import com.example.practicsing.ui.song.practice.PartPlayerScreen
import androidx.compose.ui.Modifier
import androidx.navigation.*
import com.example.practicsing.ui.song.practice.TempScreen
import com.example.practicsing.ui.song.practice.AsrScreen
import com.example.practicsing.ui.song.Test.PronunciationTestScreen
import com.example.practicsing.ui.my.SongArchiveScreen
import com.example.practicsing.ui.song.practice.component.PracticeSuccessScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.Login.route) {
            LoginScreen2(navController = navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }

        composable(Screen.Search.route) {
            SearchScreen(navController = navController)
        }

        composable("song_detail/{songId}") { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("songId") ?: ""
            SongDetailScreen(navController = navController, songId = songId)
        }


        composable("SongPractice/{songId}") { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("songId")!!
            SongPracticeScreen(
                songId = songId,
                navController = navController
            )
        }

        composable("SongPlay/{songId}") { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("songId") ?: ""
            SongPlayerScreen(songId = songId, navController = navController)
        }


        composable("PartPlay/{songId}") { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("songId") ?: ""
            PartPlayerScreen(songId = songId, navController = navController)
        }

        composable("Pronunciation/{songId}") { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("songId") ?: ""
            TempScreen(songId = songId, navController = navController)
        }

        composable(
            route = "asr_screen/{line}",
            arguments = listOf(navArgument("line") { type = NavType.StringType })
        ) { backStackEntry ->
            val line = backStackEntry.arguments?.getString("line") ?: ""
            AsrScreen(currentLine = line)
        }

        composable("pronunciation_test") {
            PronunciationTestScreen()
        }

        composable(Screen.SongArchive.route) {
            SongArchiveScreen(navController)
        }

        composable("practice_success") {
            PracticeSuccessScreen(navController)
        }


        bottomNavGraph(navController = navController)
    }
}
