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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.practicsing.R
import com.example.practicsing.navigation.Screen // 네비게이션 라우트 정의 필요
import com.example.practicsing.main.theme.MainText // 텍스트 색상
import com.example.practicsing.main.theme.Typography // 폰트 스타일
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    // ⚠️ LaunchedEffect: 컴포저블이 화면에 나타날 때 딱 한 번 실행되는 로직을 정의합니다.
    LaunchedEffect(key1 = true) {
        // 스플래시 시간 지연 (예: 2초)
        delay(2000L)

        // 다음 화면(로그인)으로 이동하고 스플래시 화면을 백 스택에서 제거합니다.
        navController.popBackStack()
        navController.navigate(Screen.Login.route) // Login Screen 라우트 정의 필요
    }

    // 🖼️ UI 구성 (스플래시 화면 캡처 이미지 참고)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(com.example.practicsing.main.theme.BasePink), // 배경 색상 (임시)
        contentAlignment = Alignment.Center
    ) {
        // 1. 배경 이미지 (복잡한 그라디언트)
        // Image(
        //     painter = painterResource(id = R.drawable.splash_background), // res/drawable-nodpi/splash_background.png 가정
        //     contentDescription = null,
        //     modifier = Modifier.fillMaxSize(),
        //     contentScale = ContentScale.Crop
        // )

        // 2. 로고와 텍스트
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // 로고 (VectorDrawable 또는 PNG)
            Image(
                painter = painterResource(id = R.drawable.ic_logo_mic), // res/drawable/ic_logo_mic.xml 가정
                contentDescription = "PractiSing Logo",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 타이틀 "PractiSing"
            Text(
                text = "PractiSing",
                color = MainText,
                style = Typography.headlineMedium, // 20sp, Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 서브타이틀 "Where the voice learns Korean"
            Text(
                text = "Where the voice learns Korean",
                color = MainText,
                style = Typography.bodyMedium, // 14sp, Medium
            )
        }
    }
}