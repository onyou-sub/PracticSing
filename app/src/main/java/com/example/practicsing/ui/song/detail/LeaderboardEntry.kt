package com.example.practicsing.ui.song.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.practicsing.data.model.LeaderboardEntry
import com.example.practicsing.main.theme.*

@Composable
fun LeaderboardList(
    entries: List<LeaderboardEntry>,
    modifier: Modifier = Modifier
) {
    if (entries.isEmpty()) {
        Text(
            text = "No records yet",
            color = Gray,
            style = Typography.bodySmall
        )
        return
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = DarkBackground.copy(alpha = 0.9f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            entries.forEachIndexed { index, entry ->
                LeaderboardRow(rank = index + 1, entry = entry)
            }
        }
    }
}

@Composable
private fun LeaderboardRow(
    rank: Int,
    entry: LeaderboardEntry
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // 순위 + 프로필
        Row {
            Text(
                text = rank.toString(),
                color = PinkAccent,
                style = Typography.bodyMedium
            )

            Spacer(Modifier.width(12.dp))

            AsyncImage(
                model = entry.profileImage,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
            )

            Spacer(Modifier.width(12.dp))

            Column {
                Text(
                    text = entry.userName,
                    color = MainText,
                    style = Typography.bodyMedium
                )
                Text(
                    text = entry.date,
                    color = Gray,
                    style = Typography.labelSmall
                )
            }
        }

        // 점수
        Text(
            text = entry.score.toString(),
            color = PinkAccent,
            style = Typography.bodyMedium
        )
    }
}
