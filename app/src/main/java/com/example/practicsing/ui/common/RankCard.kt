package com.example.practicsing.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Theme 및 Data Model Import
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.data.model.Rank // Rank 데이터 모델 사용

@Composable
fun RankCard(rank: Rank) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // 프로필 이미지 대신 임시 Box
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Gray)
            )
            Spacer(modifier = Modifier.width(12.dp))

            Column {
                // 닉네임
                Text(text = rank.name, color = MainText, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                // 노래(가수) 정보
                Text(text = "${rank.song}(${rank.singer})", color = Gray, fontSize = 12.sp)
            }
        }

        // 랭킹 점수 박스
        Box(
            modifier = Modifier
                .size(36.dp)
                .border(2.dp, PinkAccent, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = rank.score, color = PinkAccent, fontWeight = FontWeight.Bold)
        }
    }
}