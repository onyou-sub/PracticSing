package com.example.practicsing.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.practicsing.data.PracticePrefs
import com.example.practicsing.data.model.AiEvaluationResult
import com.example.practicsing.data.model.Song
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.Typography
import com.example.practicsing.navigation.Screen
import com.example.practicsing.presentation.home.HomeViewModel
import com.example.practicsing.ui.common.HomeRankCard
import com.example.practicsing.ui.common.SongCard
import com.example.practicsing.ui.common.TabButton

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // ✅ 오늘 연습 여부 / Day(연속 일수)
    val hasPracticedToday = PracticePrefs.hasPracticedToday(context)
    val currentDay = PracticePrefs.getCurrentDay(context) // 필요하면 DailyPracticeCard에 넘기기

    Column(
        modifier = Modifier
            .fillMaxSize()                 // ✅ 전체 화면 채우기
            .background(DarkBackground)    // ✅ 먼저 배경 깔고
            .verticalScroll(scrollState)   // 그 위에 스크롤
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- 1. Header ---
        Text(
            text = "Home",
            color = MainText,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 4.dp),
            style = Typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        // --- 2. Daily Practice Box ---
        DailyPracticeCard(
            hasPracticedToday = hasPracticedToday,
            onClickPractice = { navController.navigate(Screen.Practice.route) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // --- 3. HOT SONGS ---
        Text(
            text = "HOT SONGS",
            color = MainText,
            style = Typography.bodyLarge,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TabButton(
                text = "Weekly",
                selected = uiState.selectedSongTab == "Weekly"
            ) {
                viewModel.selectSongTab("Weekly")
            }
            Spacer(modifier = Modifier.width(16.dp))
            TabButton(
                text = "Monthly",
                selected = uiState.selectedSongTab == "Monthly"
            ) {
                viewModel.selectSongTab("Monthly")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        SongList(
            songs = uiState.hotSongs,
            navController = navController
        )

        Spacer(modifier = Modifier.height(30.dp))

        // --- 4. RANK 섹션 ---
        Text(
            text = "RANK",
            color = MainText,
            style = Typography.bodyLarge,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TabButton(
                text = "Weekly",
                selected = uiState.selectedRankTab == "Weekly"
            ) {
                viewModel.selectRankTab("Weekly")
            }
            Spacer(modifier = Modifier.width(16.dp))
            TabButton(
                text = "Monthly",
                selected = uiState.selectedRankTab == "Monthly"
            ) {
                viewModel.selectRankTab("Monthly")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔁 여기서 이제 AiEvaluationResult 리스트를 그대로 넘김
        RankList(results = uiState.rankResults)

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun SongList(
    songs: List<Song>,
    navController: NavController
) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(songs) { song ->
            SongCard(song, navController)
        }
    }
}

@Composable
fun RankList(results: List<AiEvaluationResult>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        results.forEachIndexed { index, result ->
            HomeRankCard(
                result = result,
                rankNumber = index + 1   // 안 쓸 거면 HomeRankCard에서 파라미터 지워도 됨
            )
        }
    }
}
