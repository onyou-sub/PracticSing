package com.example.practicsing.ui.song

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.practicsing.data.repository.EvaluationRepository
import com.example.practicsing.data.repository.SongRepositoryImpl
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.Typography
import com.example.practicsing.navigation.Screen
import com.example.practicsing.ui.song.components.LevelFilterChips
import com.example.practicsing.ui.song.components.SongCategoryTabs
import com.example.practicsing.ui.song.components.SongItem
import com.example.practicsing.ui.song.components.SongSearchBar

@Composable
fun SongScreen(
    navController: NavController,
    vm: SongViewModel = viewModel(
        factory = SongViewModelFactory(SongRepositoryImpl(), EvaluationRepository())
    )
) {
    val songs by vm.songs.collectAsState()
    val selectedCategory by vm.category.collectAsState()
    val selectedLevels by vm.selectedLevels.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            // 🔹 위에서 좀 내려오게 + 좌우 살짝 좁게
            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 1. Header
        Text(
            text = "Songs",
            color = MainText,
            style = Typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 2. SearchBar
        SongSearchBar(
            modifier = Modifier.fillMaxWidth()
        ) {
            navController.navigate(Screen.Search.route)
        }

        // 🔹 SearchBar–카테고리 간 거리 조금 띄우기
        Spacer(Modifier.height(20.dp))

        // 3. HOT/RECENT + 카테고리(가로 스크롤)
        SongCategoryTabs(
            selected = selectedCategory,
            onSelect = { vm.selectCategory(it) }
        )

        // 🔹 카테고리 바로 아래에 레벨칩
        Spacer(Modifier.height(10.dp))

        // 4. Easy / Medium / Hard
        LevelFilterChips(
            selected = selectedLevels,
            onToggle = { vm.toggleLevel(it) }
        )

        Spacer(Modifier.height(20.dp))

        // 5. 노래 리스트
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(songs) { song ->
                SongItem(song) {
                    navController.navigate(
                        Screen.SongDetail.createRoute(song.id)  // id 가 String
                    )
                }
            }
        }
    }
}
