package com.example.practicsing.navigation

sealed class Screen(val route: String) {

    // --- 1. 앱 진입 및 최상위 흐름 ---
    object Splash : Screen("splash")
    object Login : Screen("login")
    // Main은 하단 탭 바 컨테이너로 이동하는 최상위 경로입니다.
    object Main : Screen("main")
    object Register : Screen("register")

    // --- 2. BottomNavGraph (하단 내비게이션 탭 4가지) ---

    object Home : Screen("home")
    object Song : Screen("song")
    // Search에서 Song으로 네비게이션 수정
    object MyPage : Screen("mypage")
    object Search : Screen("search")

    // ⭐ 하단 탭: Practice (일일 연습 시작 페이지)
    object Practice : Screen("practice_tab_route")

    // DailyPractice: 특정 연속 일수 인수를 받는 상세 연습 페이지
    object DailyPractice : Screen("daily_practice/{streakCount}") {
        // 연속 일수를 받아 정확한 경로 문자열을 생성하는 헬퍼 함수
        fun createRoute(streakCount: Int) = "daily_practice/$streakCount"
    }

    // --- 3. 기타 상세 및 기능 화면 (탭 내부 또는 외부에서 접근) ---

    // 특정 노래 상세 페이지
    object SongDetail : Screen("song_detail/{songId}") {
        fun createRoute(songId: Long) = "song_detail/$songId"
    }

    // 특정 노래 연습 페이지
    object SongPractice : Screen("song_practice/{songId}") {
        fun createRoute(songId: Long) = "song_practice/$songId"
    }
}