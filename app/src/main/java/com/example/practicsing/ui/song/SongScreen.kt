package com.example.practicsing.ui.song

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.practicsing.data.repository.SongRepositoryImpl

import com.example.practicsing.ui.song.components.SongSearchBar
import com.example.practicsing.ui.song.components.SongCategoryTabs
import com.example.practicsing.ui.song.components.LevelFilterChips
import com.example.practicsing.ui.song.components.SongItem

@Composable
fun SongScreen(
    navController: NavHostController,
    vm: SongViewModel = viewModel(factory = SongViewModelFactory(SongRepositoryImpl()))
) {
    val songs by vm.songs.collectAsState()
    val selectedCategory by vm.category.collectAsState()
    val selectedLevels by vm.selectedLevels.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {

        SongSearchBar {
            navController.navigate("search")
        }

        Spacer(Modifier.height(16.dp))

        SongCategoryTabs(
            selected = selectedCategory,
            onSelect = { vm.selectCategory(it) }
        )

        Spacer(Modifier.height(12.dp))

        LevelFilterChips(
            selected = selectedLevels,
            onToggle = { vm.toggleLevel(it) }
        )

        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            items(songs) { song ->
                SongItem(song) {
                    navController.navigate("songDetail/${song.id}")
                }
            }
        }
    }
}
