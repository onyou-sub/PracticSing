@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.practicsing.ui.eval

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.practicsing.data.model.PracticeRecord
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Typography
import com.example.practicsing.navigation.Screen
import com.example.practicsing.ui.common.AppScreenContainer

@Composable
fun AiEvaluationScreen(
    navController: NavController,
    record: PracticeRecord
) {
    AppScreenContainer(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // 상태바와 겹치지 않게
        Spacer(Modifier.height(8.dp))

        // 상단 바 : Back / Title / Home
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MainText
                )
            }

            Text(
                text = "AI Evaluation",
                color = MainText,
                style = Typography.bodyLarge
            )

            IconButton(onClick = { navController.navigate(Screen.Home.route) }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = MainText
                )
            }
        }

        // 메인 카드
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF181818)
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // 곡 정보 + 점수
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = record.songTitle,
                            color = MainText,
                            style = Typography.titleLarge
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = record.artist,
                            color = MainText,
                            style = Typography.bodySmall
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(PinkAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        val scoreText = record.aiScore?.toString() ?: "--"
                        Text(
                            text = scoreText,
                            color = MainText,
                            style = Typography.titleLarge
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // 녹음 정보
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = record.albumImageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Recording",
                            color = MainText,
                            style = Typography.bodyMedium
                        )
                        Text(
                            text = record.durationText,
                            color = Gray,
                            style = Typography.labelSmall
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Strength
                Text(
                    text = "Strength",
                    color = MainText,
                    style = Typography.bodyMedium
                )
                Spacer(Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF202020))
                        .padding(12.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    Text(
                        text = record.aiStrengthComment
                            ?: "Your pronunciation feedback will appear here.",
                        color = MainText,
                        style = Typography.bodySmall
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Improvement
                Text(
                    text = "Improvement",
                    color = MainText,
                    style = Typography.bodyMedium
                )
                Spacer(Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF202020))
                        .padding(12.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    Text(
                        text = record.aiImprovementComment
                            ?: "Suggestions for improvement will appear here.",
                        color = MainText,
                        style = Typography.bodySmall
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // 아래 버튼 영역
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    // TODO: 기록 저장
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PinkAccent,
                    contentColor = MainText
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("SAVE", style = Typography.titleMedium)
            }

            Spacer(Modifier.height(8.dp))

            TextButton(
                onClick = {
                    // TODO: 다시 부르기 -> 연습 화면
                    // navController.navigate(Screen.SongPractice.createRoute(record.songId))
                }
            ) {
                Text(
                    text = "Retry",
                    color = MainText,
                    style = Typography.bodyMedium
                )
            }
        }
    }
}
