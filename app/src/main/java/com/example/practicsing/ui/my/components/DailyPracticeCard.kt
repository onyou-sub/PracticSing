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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.Typography

@Composable
fun DailyPracticeCard(
    dateLabel: String,
    streakCount: Int,
    totalSlots: Int,
    practicedToday: Boolean
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

            Row(verticalAlignment = Alignment.CenterVertically) {

                // 왼쪽 날짜 + 큰 원 (streak)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = dateLabel, color = Gray, style = Typography.labelSmall)
                    Spacer(Modifier.height(6.dp))

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(
                                if (practicedToday) PinkAccent else Color(0xFF303030)
                            ),
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


                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(totalSlots - 1) { index ->

                        val isTodaySlot = (index == 0 && practicedToday)

                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isTodaySlot) PinkAccent else Color(0xFF303030)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isTodaySlot) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Checked",
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

