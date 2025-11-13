package com.example.practicsing.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.practicsing.R
import com.example.practicsing.navigation.Screen // 네비게이션 라우트 정의 필요
import com.example.practicsing.main.theme.MainText // 텍스트 색상
import com.example.practicsing.main.theme.Typography // 폰트 스타일
import kotlinx.coroutines.delay

// 기존 SplashScreen 코드는 그대로 둡니다.
@Composable
fun SplashScreen(navController: NavController) {
    // ... LaunchedEffect 및 UI 로직 ...
    // ... (UI 로직은 아래 SplashScreenContent에 위임) ...

    LaunchedEffect(key1 = true) {
        delay(2000L)
        navController.popBackStack()
        navController.navigate(Screen.Login.route)
    }

    // UI 부분을 별도의 함수로 분리하여 호출
    SplashScreenContent()
}

// ----------------------------------------------------
// ✨ 프리뷰를 위해 추가된 함수 (인수가 없어야 함)
@Preview
@Composable
fun SplashScreenPreview() {
    // LaunchedEffect처럼 navigation 로직을 포함하지 않고,
    // 오직 UI 컴포넌트만 렌더링하도록 분리된 함수를 호출합니다.
    SplashScreenContent()
}
// ----------------------------------------------------


// 🖼️ UI 구성만 담당하는 함수 (Navigation과 분리)
@Composable
fun SplashScreenContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(com.example.practicsing.main.theme.BasePink), // 배경 색상 (임시)
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // 로고 (VectorDrawable 또는 PNG)
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "PracticSing Logo",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 타이틀 "PracticSing"
            Text(
                text = "PracticSing",
                color = MainText,
                style = Typography.headlineMedium,
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 서브타이틀 "Where the voice learns Korean"
            Text(
                text = "Where the voice learns Korean",
                color = MainText,
                style = Typography.bodyMedium,
            )
        }
    }
}