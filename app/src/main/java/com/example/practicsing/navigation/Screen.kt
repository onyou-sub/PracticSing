package com.example.practicsing.navigation

sealed class Screen(val route: String) {
    // 🚨 AppNavHost에서 사용할 최상위 라우트들
    object Splash : Screen("splash_screen")
    object Login : Screen("login_screen")
    object Main : Screen("main_app_route") // 하단 탭 네비게이션의 시작 라우트

    // 탭 바에서 사용할 라우트들 (BottomNavGraph에서 사용)
    object Home : Screen("home_screen")
    object Song : Screen("song_screen")
    object Rank : Screen("rank_screen")
    object My : Screen("my_screen")
}