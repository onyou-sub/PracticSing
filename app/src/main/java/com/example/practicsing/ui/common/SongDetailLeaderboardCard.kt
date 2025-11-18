package com.example.practicsing.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.practicsing.data.model.LeaderboardEntry
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import androidx.compose.ui.text.font.FontWeight

@Composable
fun LeaderboardCard(
    entry: LeaderboardEntry,
    rank: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 순위 표시 (1,2,3)
        Text(
            text = rank.toString(),
            color = PinkAccent,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.width(12.dp))

        // 프로필 이미지 (없으면 Gray 배경 박스)
        AsyncImage(
            model = entry.profileImage,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Gray)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = entry.userName,
                color = MainText,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        // 점수
        Box(
            modifier = Modifier
                .size(36.dp)
                .border(2.dp, PinkAccent, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = entry.score.toString(),
                color = PinkAccent,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
