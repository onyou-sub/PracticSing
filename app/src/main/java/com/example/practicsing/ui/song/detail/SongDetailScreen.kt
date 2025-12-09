package com.example.practicsing.ui.song.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practicsing.data.model.AiEvaluationResult
import com.example.practicsing.data.model.Song
import com.example.practicsing.data.repository.EvaluationRepository
import com.example.practicsing.data.repository.SongRepositoryImpl
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Typography
import com.example.practicsing.ui.common.RoundedBackButton

@Composable
fun SongDetailScreen(
    navController: NavController,
    songId: String,
    songRepo: SongRepositoryImpl = SongRepositoryImpl(),
    evalRepo: EvaluationRepository = EvaluationRepository()
) {
    // ✅ 곡 정보 조회
    val song: Song? = remember { songRepo.getSongs().firstOrNull { it.id == songId } }

    if (song == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground),
            contentAlignment = Alignment.Center
        ) {
            Text("Song not found", color = MainText)
        }
        return
    }

    // ✅ 이 곡에 대한 Evaluation 기반 리더보드
    var leaderboard by remember { mutableStateOf<List<AiEvaluationResult>>(emptyList()) }

    LaunchedEffect(song.id) {
        leaderboard = evalRepo.getSongLeaderboard(song.id, limit = 20)
        println("🔥 Loaded leaderboard count = ${leaderboard.size}")
        leaderboard.forEach {
            println("🔥 user=${it.userId}, score=${it.score}")
        }
    }

    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = DarkBackground,
        bottomBar = {
            Button(
                onClick = {
                    navController.navigate("SongPractice/${song.id}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PinkAccent)
            ) {
                Text("Start", color = Color.White, style = Typography.titleMedium)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
                .padding(innerPadding)
        ) {
            // 🔹 전체 스크롤 영역
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                // 상단: 앨범 이미지 딤 + 곡 정보 카드
                SongDetailContent(song = song)

                Spacer(Modifier.height(8.dp))

                // Leaderboard 섹션
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "Leaderboard",
                        color = MainText,
                        style = Typography.bodyLarge
                    )
                    Spacer(Modifier.height(12.dp))

                    // 🔹 아까 만든 SongLeaderboardList 사용
                    SongLeaderboardList(results = leaderboard)
                }

                Spacer(Modifier.height(80.dp)) // bottomBar와 간격
            }

            // 좌상단 둥근 Back 버튼
            RoundedBackButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 16.dp),
                onClick = { navController.popBackStack() }
            )
        }
    }
}
