package com.example.practicsing.ui.song

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.practicsing.data.repository.SongRepositoryImpl
import com.example.practicsing.navigation.Screen

// 🔹 Home이랑 동일한 테마 import
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.Typography
import androidx.compose.material3.Text

import com.example.practicsing.ui.song.components.SongSearchBar
import com.example.practicsing.ui.song.components.SongCategoryTabs
import com.example.practicsing.ui.song.components.LevelFilterChips
import com.example.practicsing.ui.song.components.SongItem

import androidx.compose.foundation.background
import androidx.compose.ui.Alignment


@Composable
fun SongScreen(
    navController: NavController,
    vm: SongViewModel = viewModel(factory = SongViewModelFactory(SongRepositoryImpl()))
) {
    val songs by vm.songs.collectAsState()
    val selectedCategory by vm.category.collectAsState()
    val selectedLevels by vm.selectedLevels.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)          // ✅ Home과 동일 배경
            .padding(20.dp),                    // ✅ Home과 동일 패딩
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // --- 1. Header (Home과 동일 스타일) ---
        Text(
            text = "Songs",
            color = MainText,
            style = Typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 4.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        // --- 2. 검색바 ---
        SongSearchBar(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 문자열 말고 Screen 정의 써주는 게 안전
            navController.navigate(Screen.Search.route)
        }

        Spacer(Modifier.height(16.dp))

        // --- 3. 카테고리 탭 ---
        SongCategoryTabs(
            selected = selectedCategory,
            onSelect = { vm.selectCategory(it) }
        )

        Spacer(Modifier.height(12.dp))

        // --- 4. 레벨 필터 칩 ---
        LevelFilterChips(
            selected = selectedLevels,
            onToggle = { vm.toggleLevel(it) }
        )

        Spacer(Modifier.height(20.dp))

        // --- 5. 곡 리스트 ---
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(songs) { song ->
                SongItem(song) {
                    // 이 부분도 route 문자열 말고 Screen 사용
                    navController.navigate(
                        Screen.SongDetail.createRoute(song.id.toString())
                    )
                }
            }
        }
    }
}
