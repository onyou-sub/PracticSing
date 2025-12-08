package com.example.practicsing.ui.song.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicsing.data.model.AiEvaluationResult
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent

@Composable
fun SongLeaderboardList(results: List<AiEvaluationResult>) {
    if (results.isEmpty()) {
        Text("No records yet.", color = Gray, fontSize = 12.sp)
        return
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFF1E1E1E))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        results.take(3).forEachIndexed { index, result ->
            SongLeaderboardRow(
                rank = index + 1,
                result = result
            )
            if (index < results.size - 1) {
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun SongLeaderboardRow(
    rank: Int,
    result: AiEvaluationResult
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            // 왼쪽 순위 동그라미
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2A2A2A)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = rank.toString(),
                    color = PinkAccent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.width(12.dp))

            Column {
                val displayName =
                    if (result.userId.isNotBlank()) result.userId else "Guest"

                Text(
                    text = displayName,
                    color = MainText,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    text = result.practicedDateText,
                    color = Gray,
                    fontSize = 12.sp
                )
            }
        }

        // 오른쪽 점수 동그라미
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
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
