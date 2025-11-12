package com.example.practicsing.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items // LazyRow의 items 확장 함수 사용을 위해 필수 Import
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip

// ⭐ RecordVoiceOver 아이콘 사용을 위한 필수 Import
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RecordVoiceOver

import androidx.compose.ui.text.font.FontWeight
// ⭐ viewModel() 함수 사용을 위한 필수 Import
import androidx.lifecycle.viewmodel.compose.viewModel

// ===================================================
// 테마 및 공통 컴포넌트 Import (프로젝트 구조: main.theme, ui.common 사용)
import com.example.practicsing.main.theme.BasePink
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.LightPink
import com.example.practicsing.main.theme.Typography // 정의된 커스텀 Typography 사용

import com.example.practicsing.ui.common.TabButton
import com.example.practicsing.ui.common.SongCard
import com.example.practicsing.ui.common.RankCard

// 데이터 모델 및 ViewModel Import (클린 아키텍처 구조 가정)
import com.example.practicsing.data.model.Song
import com.example.practicsing.data.model.Rank
import com.example.practicsing.presentation.home.HomeViewModel
// ===================================================

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    // ViewModel의 상태를 수집 (StateFlow를 관찰)
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(scrollState)
            .background(DarkBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- 1. Header ---
        Text(
            "Home",
            color = MainText,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 4.dp),
            style = Typography.headlineSmall // 24sp, Bold
        )
        Spacer(modifier = Modifier.height(20.dp))

        // --- 2. Daily Practice Box ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(LightPink, BasePink)
                    )
                ),
            contentAlignment = Alignment.TopCenter
        ){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(15.dp))
                // ⭐ RecordVoiceOver 아이콘 사용
                Icon(Icons.Filled.RecordVoiceOver, contentDescription = "Daily Practice", tint = MainText, modifier = Modifier.size(50.dp))
                Spacer(modifier = Modifier.height(8.dp))

                // Typography 사용: 인삿말 title (headlineMedium)
                Text(
                    text = "Elevate Your Voice Daily",
                    color = MainText,
                    style = Typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(2.dp))

                // Typography 사용: 작은 알림 (labelSmall)
                Text(
                    text = "Just for 3 minutes",
                    color = Gray,
                    style = Typography.labelSmall
                )
                Spacer(modifier = Modifier.height(2.dp))

                Button(
                    onClick = { /* TODO: navigate to Pract Screen */ },
                    colors = ButtonDefaults.buttonColors(containerColor = PinkAccent),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    // Typography 사용: CTA (titleMedium)
                    Text(
                        "Start Daily Practice",
                        color = MainText,
                        style = Typography.titleMedium
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        // --- 3. HOT SONGS 섹션 ---
        Text(
            text = "HOT SONGS",
            color = MainText,
            style = Typography.bodyLarge, // 16sp Medium (혹은 별도 정의된 스타일 사용)
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(6.dp))

        // 탭 버튼 (ViewModel 로직 사용)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ){
            TabButton("Weekly", uiState.selectedSongTab =="Weekly"){
                viewModel.selectSongTab("Weekly")
            }
            Spacer(modifier = Modifier.width(16.dp))
            TabButton("Monthly", uiState.selectedSongTab =="Monthly"){
                viewModel.selectSongTab("Monthly")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        // 노래 리스트 (상태에 따라 표시)
        SongList(songs = uiState.hotSongs)

        Spacer(modifier = Modifier.height(30.dp))

        // --- 4. RANK 섹션 ---
        Text(
            text = "RANK",
            color = MainText,
            style = Typography.bodyLarge, // 16sp Medium (혹은 별도 정의된 스타일 사용)
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(6.dp))

        // 랭크 탭 버튼 (ViewModel 로직 사용)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TabButton("Weekly", uiState.selectedRankTab == "Weekly") {
                viewModel.selectRankTab("Weekly")
            }
            Spacer(modifier = Modifier.width(16.dp))
            TabButton("Monthly", uiState.selectedRankTab == "Monthly") {
                viewModel.selectRankTab("Monthly")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 랭크 리스트 (상태에 따라 표시)
        RankList(ranks = uiState.ranking)

        Spacer(modifier = Modifier.height(20.dp))
    }
}

// -------------------------------------------------------------
// * List Composable (LazyRow/Column 사용 시 Import 경로 유의)
// -------------------------------------------------------------

@Composable
fun SongList(songs: List<Song>) { // data.model.Song 타입 사용
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        // items() 확장 함수는 androidx.compose.foundation.lazy.items import 필요
        items(songs) { song ->
            SongCard(song)
        }
    }
}

@Composable
fun RankList(ranks: List<Rank>) { // data.model.Rank 타입 사용
    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    ) {
        ranks.forEach { rank ->
            RankCard(rank)
        }
    }
}