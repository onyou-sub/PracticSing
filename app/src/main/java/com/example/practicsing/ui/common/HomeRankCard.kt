package com.example.practicsing.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicsing.R
import com.example.practicsing.data.model.AiEvaluationResult
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent

@Composable
fun HomeRankCard(
    result: AiEvaluationResult,
    // 선택: 1위, 2위, 3위 같은 랭크 넘버 표시하고 싶으면
    rankNumber: Int? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            // ✅ 공통 기본 프로필 이미지 (Mypage에서 쓰는 이모지랑 동일한 drawable 사용)
            Image(
                painter = painterResource(id = R.drawable.ic_default_profile), // 네가 만든 기본 아이콘
                contentDescription = "Profile",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Gray)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                // userId 그대로 쓰기 민망하면 여기서 마스킹해도 됨
                val displayName = if (result.userId.isNotBlank()) {
                    result.userId
                } else {
                    "Guest"
                }

                Text(
                    text = displayName,
                    color = MainText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Text(
                    text = "${result.songTitle} (${result.artist})",
                    color = Gray,
                    fontSize = 12.sp
                )
            }
        }

        // 오른쪽 동그라미 안에 점수
        Box(
            modifier = Modifier
                .size(36.dp)
                .border(2.dp, PinkAccent, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = result.score.toString(),
                color = PinkAccent,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
