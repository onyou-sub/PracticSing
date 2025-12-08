package com.example.practicsing.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.practicsing.R
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Typography

@Composable
fun DailyPracticeCard(
    hasPracticedToday: Boolean,
    onClickPractice: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        // 공통 배경 이미지
        AsyncImage(
            model = R.drawable.home,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        if (hasPracticedToday) {
            // ✅ 이미 오늘 연습을 끝낸 상태
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.55f))
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Filled.RecordVoiceOver,
                    contentDescription = "Daily Practice Done",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Good Job!",
                    color = Color.White,
                    style = Typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "You've already done today's exercise.",
                    color = Color.LightGray,
                    style = Typography.labelSmall
                )
            }
        } else {
            // ⭕ 아직 연습 안 한 상태
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xAAFF3AAE),
                                Color(0xAAFF0088)
                            )
                        )
                    )
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(15.dp))

                Icon(
                    Icons.Filled.RecordVoiceOver,
                    contentDescription = "Daily Practice",
                    tint = MainText,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Elevate Your Voice Daily",
                    color = MainText,
                    style = Typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Just for 3 minutes",
                    color = Gray,
                    style = Typography.labelSmall
                )
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onClickPractice,
                    colors = ButtonDefaults.buttonColors(containerColor = PinkAccent),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(
                        horizontal = 24.dp,
                        vertical = 8.dp
                    )
                ) {
                    Text(
                        "Click here to join",
                        color = MainText,
                        style = Typography.titleMedium
                    )
                }
            }
        }
    }
}
