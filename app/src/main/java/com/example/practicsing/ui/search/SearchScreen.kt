package com.example.practicsing.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.practicsing.data.repository.EvaluationRepository
import com.example.practicsing.data.repository.SongRepositoryImpl
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.Gray
import com.example.practicsing.main.theme.Typography
import com.example.practicsing.navigation.Screen
import com.example.practicsing.ui.song.components.SongItem  // 검색 결과에 SongItem 재사용
import androidx.compose.material3.TextFieldDefaults


@Composable
fun SearchScreen(
    navController: NavController,
    vm: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(SongRepositoryImpl(), EvaluationRepository())
    )
) {
    val query by vm.query.collectAsState()
    val recent by vm.recentQueries.collectAsState()
    val results by vm.results.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(20.dp)
    ) {
        // --- 상단 Back + 검색 필드 ---
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MainText
                )
            }

            Spacer(Modifier.width(8.dp))

            TextField(
                value = query,
                onValueChange = { vm.onQueryChange(it) },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                placeholder = { Text("Search for favorite song") },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Gray)
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = DarkBackground,
                    unfocusedContainerColor = DarkBackground,
                    focusedTextColor = MainText,
                    unfocusedTextColor = MainText,
                    cursorColor = MainText,
                    focusedIndicatorColor = Gray,
                    unfocusedIndicatorColor = Gray
                )
            )

        }

        Spacer(Modifier.height(24.dp))

        if (query.isBlank()) {
            // === ① 검색어가 없을 땐 Recent Query 화면 (피그마 2번째) ===
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Recent Query", color = MainText, style = Typography.bodyLarge)
                Text(
                    text = "Delete All",
                    color = Gray,
                    style = Typography.labelMedium,
                    modifier = Modifier.clickable { vm.clearAllRecent() }
                )
            }

            Spacer(Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recent) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                vm.onQueryChange(item)
                                vm.submitQuery()
                            }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Gray,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(text = item, color = MainText, style = Typography.bodyMedium)
                        Spacer(Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Gray,
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { vm.deleteRecent(item) }
                        )
                    }
                }
            }
        } else {
            // === ② 검색어가 있을 땐 검색 결과 리스트 ===
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(results) { song ->
                    SongItem(song = song) {
                        vm.submitQuery()
                        navController.navigate(
                            Screen.SongDetail.createRoute(song.id.toString())
                        )
                    }
                }
            }
        }
    }
}
