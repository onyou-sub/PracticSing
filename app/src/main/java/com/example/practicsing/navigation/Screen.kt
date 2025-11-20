package com.example.practicsing.navigation

sealed class Screen(val route: String) {

    // --- 1. 앱 진입 및 최상위 흐름 ---
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main") // Bottom Nav 그룹의 라우트

    // --- 2. Bottom Navigation 탭 ---
    object Home : Screen("home")
    object Song : Screen("song_list")
    object Practice : Screen("practice_tab_route")
    object MyPage : Screen("mypage")

    // --- 3. 최상위 상세 화면 (SongScreen에서 호출됨) ---
    object Search : Screen("search")

    object SongDetail : Screen("song_detail/{songId}") {
        fun createRoute(songId: String) = "song_detail/$songId"
    }

    object SongPractice : Screen("song_practice/{songId}") {
        fun createRoute(songId: Long) = "song_practice/$songId"
    }

    object DailyPractice : Screen("daily_practice/{streakCount}") {
        fun createRoute(streakCount: Int) = "daily_practice/$streakCount"
    }

    object SongArchive : Screen("song_archive")

    object AiEvaluation : Screen("ai_evaluation/{recordId}") {
        fun createRoute(recordId: String) = "ai_evaluation/$recordId"
    }
}