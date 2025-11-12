package com.example.practicsing.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow // LazyRow 사용
import androidx.compose.foundation.lazy.items // LazyRow의 items 확장 함수 사용을 위해 추가
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel

// ===================================================
// ** 경로 수정 **
import com.example.practicsing.main.theme.BasePink
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.PinkAccent
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.LightPink

import com.example.practicsing.ui.common.TabButton
import com.example.practicsing.ui.common.SongCard
import com.example.practicsing.ui.common.RankCard

import com.example.practicsing.data.model.Song // SongList 인자 타입 경로
import com.example.practicsing.data.model.Rank // RankList 인자 타입 경로
import com.example.practicsing.presentation.home.HomeViewModel // ViewModel 경로 (ui.home이 아닌 presentation.home에 있다고 가정)
// ===================================================


@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    // ViewModel의 상태를 수집
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
        // ... (Header 및 Daily Practice Box 코드는 동일) ...
        // --- 1. Header ---
        Text(
            "Home",
            color = MainText,
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(15.dp),
            style = MaterialTheme.typography.headlineSmall
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
            // Daily Practice Box 내부 콘텐츠 (생략)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(15.dp))
                Icon(Icons.Filled.RecordVoiceOver, contentDescription = "Daily Practice", tint = MainText, modifier = Modifier.size(50.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Elevate Your Voice Daily", color = MainText, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Just for 3 minutes", color = Gray, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Button(
                    onClick = { /* TODO: navigate to Pract Screen */ },
                    colors = ButtonDefaults.buttonColors(containerColor = PinkAccent),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    Text("Start Daily Practice", color = MainText)
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        // --- 3. HOT SONGS 섹션 ---
        Text(
            text = "HOT SONGS",
            color = MainText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
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
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
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
// * List Composable (Song/Rank 모델 경로 수정)
// -------------------------------------------------------------

@Composable
fun SongList(songs: List<Song>) { // data.model.Song 타입 사용
    LazyRow( // LazyRow 사용
        modifier = Modifier.fillMaxWidth()
    ) {
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