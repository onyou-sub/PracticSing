package com.example.practicsing.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practicsing.R
import com.example.practicsing.navigation.Screen
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.Typography
import com.example.practicsing.main.theme.BasePink
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    // ⭐ BuildConfig 제거 → 바로 네비게이션 가능
    LaunchedEffect(Unit) {
        delay(1200)

        // 기본: Login 화면으로 이동
        navController.popBackStack()
        navController.navigate(Screen.Login.route)
    }

    SplashScreenContent()
}

@Composable
fun SplashScreenContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BasePink),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // 앱 로고
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(80.dp)
            )

            Spacer(Modifier.height(20.dp))

            // 앱 이름
            Text(
                text = "PracticSing",
                color = MainText,
                style = Typography.headlineMedium
            )

            Spacer(Modifier.height(4.dp))

            // 서브 텍스트
            Text(
                text = "Where the voice learns Korean",
                color = MainText,
                style = Typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreenContent()
}
