package com.example.practicsing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.practicsing.ui.home.HomeScreen
import com.example.practicsing.ui.song.SongScreen
import com.example.practicsing.ui.my.MyScreen
import com.example.practicsing.ui.pract.PracticeScreen
import com.example.practicsing.ui.auth.LoginScreen
import com.example.practicsing.ui.auth.LoginScreen2
import com.example.practicsing.ui.auth.RegisterScreen
import com.example.practicsing.ui.splash.SplashScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    // NavController 생성


    // NavHost로 네비게이션 설정
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen2(navController = navController)
        }

        composable(Screen.Register.route){
            RegisterScreen(navController = navController)
        }

        composable(Screen.Main.route){
            BottomNavGraph(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }


        composable(Screen.Song.route) {
            SongScreen(navController = navController)
        }

        composable(Screen.Practice.route) {
            PracticeScreen(navController = navController)  // Practice 화면
        }


        composable(Screen.MyPage.route) {
            MyScreen()  // MyPage 화면
        }


        composable(Screen.Search.route) {
           // SearchScreen()  // Search 화면
        }


    }
}
