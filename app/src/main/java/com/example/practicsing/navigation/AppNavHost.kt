package com.example.practicsing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.practicsing.presentation.splash.SplashScreen // ⭐ 새로 정의한 Splash Screen
import androidx.compose.ui.Modifier // Modifiers 사용 시 필요 (Box, Text 등 임시 컴포넌트 포함 시)
import androidx.compose.foundation.layout.fillMaxSize // fillMaxSize 사용 시 필요

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        // 🚨 시작점을 Splash Screen 라우트로 설정
        startDestination = Screen.Splash.route
    ) {

        // 1. Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        // 2. Login Screen (스플래시에서 이동)
        composable(Screen.Login.route) {
            // TODO: LoginScreen.kt 구현 후 여기에 연결
            LoginScreenPlaceholder(navController = navController) // 임시 컴포저블 사용
        }

        // 3. Main Bottom Navigation Graph (로그인 완료 후 진입)
        // 로그인/스플래시 이후 앱의 메인 영역은 BottomNavGraph를 통해 관리됩니다.
        composable(Screen.Main.route) {
            BottomNavGraph(navController = navController) // 하단 탭 전체를 포함하는 그래프
        }
    }
}

// ⚠️ 임시 컴포넌트: LoginScreen 구현 전까지 사용
@Composable
private fun LoginScreenPlaceholder(navController: NavController) {
    // 임시로 로그인 후 바로 Home으로 이동하도록 설정 (실제 구현 시 제거)
    androidx.compose.foundation.layout.Box(
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.Text("Login Screen Placeholder. Auto-navigating to Home...",
            modifier = androidx.compose.ui.Modifier.clickable {
                navController.navigate(Screen.Main.route)
            }
        )
    }
}