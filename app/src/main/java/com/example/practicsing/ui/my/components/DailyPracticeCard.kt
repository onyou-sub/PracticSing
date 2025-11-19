// file: app/src/main/java/com/example/practicsing/ui/my/components/DailyPracticeCard.kt
package com.example.practicsing.ui.my.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.Typography

@Composable
fun DailyPracticeCard(
    dateLabel: String,     // "11.10"
    streakCount: Int,      // 오늘까지 연속일 수
    totalSlots: Int        // 도트 개수 (예: 7)
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF181818)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Daily Practice",
                color = MainText,
                style = Typography.bodyMedium
            )

            Spacer(Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 오늘 날짜 + 분홍 동그라미
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = dateLabel, color = Gray, style = Typography.labelSmall)
                    Spacer(Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(PinkAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = streakCount.toString(),
                            color = MainText,
                            style = Typography.bodyMedium
                        )
                    }
                }

                Spacer(Modifier.width(12.dp))

                // 남은 도트들
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val remain = (totalSlots - 1).coerceAtLeast(0)
                    repeat(remain) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF303030))
                        )
                    }
                }
            }
        }
    }
}
