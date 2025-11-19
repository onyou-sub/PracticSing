package com.example.practicsing.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.systemBarsPadding
import com.example.practicsing.main.theme.DarkBackground

@Composable
fun AppScreenContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .systemBarsPadding() // 상태바 / 네비바 피하기
                .padding(
                    top = 32.dp,      // ✅ 여기 숫자로 “헤더 얼마나 내릴지” 한 번에 조절
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 16.dp
                )
                .widthIn(max = 480.dp) // 콘텐츠 최대 너비
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            content()
        }
    }
}
