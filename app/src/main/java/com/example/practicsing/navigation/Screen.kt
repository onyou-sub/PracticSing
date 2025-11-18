package com.example.practicsing.navigation

sealed class Screen(val route: String) {

    // --- 1. 앱 진입 및 최상위 흐름 ---
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")

    // --- 2. Bottom Navigation 탭 ---
    object Home : Screen("home")
    object Song : Screen("song_list")   // ← 수정됨 (song → song_list)
    object Practice : Screen("practice_tab_route")
    object MyPage : Screen("mypage")

    // 검색 화면
    object Search : Screen("search")

    // --- 3. 상세 화면 ---
    object SongDetail : Screen("song_detail/{songId}") {
        fun createRoute(songId: Long) = "song_detail/$songId"
    }

    object SongPractice : Screen("song_practice/{songId}") {
        fun createRoute(songId: Long) = "song_practice/$songId"
    }

    object DailyPractice : Screen("daily_practice/{streakCount}") {
        fun createRoute(streakCount: Int) = "daily_practice/$streakCount"
    }
}
