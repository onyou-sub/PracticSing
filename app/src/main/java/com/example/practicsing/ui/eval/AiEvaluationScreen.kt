package com.example.practicsing.ui.eval

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.practicsing.data.model.PracticeRecord
import com.example.practicsing.data.model.toAiEvaluationResult
import com.example.practicsing.data.repository.EvaluationRepository
import com.example.practicsing.data.repository.SongRepositoryImpl
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Typography
import com.example.practicsing.navigation.Screen
import com.example.practicsing.ui.common.AppScreenContainer
import kotlinx.coroutines.launch

@Composable
fun AiEvaluationScreen(
    navController: NavController,
    record: PracticeRecord
) {
    val repo = remember { EvaluationRepository() }
    val songRepo = remember { SongRepositoryImpl() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // 🔥 로그인 유저 ID
    val prefs = remember {
        context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }
    val userId = remember { prefs.getString("userid", "") ?: "" }

    AppScreenContainer(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {

        Spacer(Modifier.height(8.dp))

        // 상단 바
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

            Text("AI Evaluation", color = MainText, style = Typography.bodyLarge)

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
            colors = CardDefaults.cardColors(Color(0xFF181818)),
            shape = RoundedCornerShape(20.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

                // 곡 정보 & 점수
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column {
                        Text(record.songTitle, color = MainText, style = Typography.titleLarge)
                        Spacer(Modifier.height(4.dp))
                        Text(record.artist, color = MainText, style = Typography.bodySmall)
                    }

                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(PinkAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = record.aiScore?.toString() ?: "--",
                            color = MainText,
                            style = Typography.titleLarge
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // 녹음 정보
                Row(verticalAlignment = Alignment.CenterVertically) {

                    AsyncImage(
                        model = record.albumImageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                    )

                    Spacer(Modifier.width(12.dp))

                    Column {
                        Text("Recording", color = MainText, style = Typography.bodyMedium)
                        Text(record.durationText, color = Gray, style = Typography.labelSmall)
                    }
                }

                Spacer(Modifier.height(24.dp))

                Text("Strength", color = MainText, style = Typography.bodyMedium)
                Spacer(Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF202020))
                        .padding(12.dp)
                ) {
                    Text(record.aiStrengthComment ?: "", color = MainText, style = Typography.bodySmall)
                }

                Spacer(Modifier.height(16.dp))

                Text("Improvement", color = MainText, style = Typography.bodyMedium)
                Spacer(Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF202020))
                        .padding(12.dp)
                ) {
                    Text(record.aiImprovementComment ?: "", color = MainText, style = Typography.bodySmall)
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // SAVE 버튼
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = {
                    coroutineScope.launch {
                        val result = record.toAiEvaluationResult(userId)
                        repo.saveEvaluation(result)
                        songRepo.savePracticeRecord(record)
                        navController.navigate(Screen.Home.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(PinkAccent, MainText)
            ) {
                Text("SAVE", style = Typography.titleMedium)
            }

            Spacer(Modifier.height(8.dp))

            TextButton(
                onClick = {
                    navController.navigate(Screen.SongPractice.createRoute(record.songId))
                }
            ) {
                Text("Retry", color = MainText)
            }
        }
    }
}
