package com.example.practicsing.ui.song.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practicsing.data.model.Song
import com.example.practicsing.data.model.LeaderboardEntry
import com.example.practicsing.data.repository.SongRepositoryImpl
import com.example.practicsing.domain.usecase.GetLeaderboardUseCase
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Typography
import com.example.practicsing.ui.common.RoundedBackButton

@Composable
fun SongDetailScreen(
    navController: NavController,
    songId: String,
    repo: SongRepositoryImpl = SongRepositoryImpl()
) {
    // 곡 정보
    val song: Song? = remember { repo.getSongs().firstOrNull { it.id == songId } }

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

    // 리더보드 데이터 (UseCase 사용)
    val leaderboard: List<LeaderboardEntry> by remember {
        mutableStateOf(
            GetLeaderboardUseCase(repo)(song.id)
        )
    }

    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = DarkBackground,
        bottomBar = {
            Button(
                onClick = {
                    // TODO: 나중에 SongPractice 화면 연결
                    // navController.navigate(Screen.SongPractice.createRoute(song.id))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PinkAccent)
            ) {
                Text("Start", color = MainText, style = Typography.titleMedium)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // 메인 컨텐츠 (스크롤)
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                // 상단 요약 카드
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

                    LeaderboardList(entries = leaderboard)
                }

                Spacer(Modifier.height(80.dp)) // bottomBar와 살짝 간격
            }

            // 좌상단 동그라미 Back 버튼
            RoundedBackButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 16.dp),
                onClick = { navController.popBackStack() }
            )
        }
    }
}
