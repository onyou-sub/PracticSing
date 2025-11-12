package com.example.practicsing.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Theme Import (경로에 맞게 수정: main.theme 사용)
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.MainText

@Composable
fun TabButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = text,
        // 선택된 상태에 따라 색상 변경
        color = if (selected) MainText else Gray,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .clickable(onClick = onClick) // 클릭 이벤트 처리
            .padding(bottom = 4.dp)
    )
}