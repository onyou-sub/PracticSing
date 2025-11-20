@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.practicsing.ui.my

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.practicsing.data.model.PracticeRecord
import com.example.practicsing.main.theme.DarkBackground
import com.example.practicsing.main.theme.MainText
import com.example.practicsing.main.theme.Typography
import com.example.practicsing.navigation.Screen
import com.example.practicsing.ui.common.AppScreenContainer
import com.example.practicsing.ui.my.components.SongArchivePreviewCard
import com.example.practicsing.ui.my.viewmodel.SongArchiveViewModel
import com.example.practicsing.ui.my.viewmodel.SongArchiveViewModelFactory
import com.example.practicsing.data.repository.SongRepositoryImpl

@Composable
fun SongArchiveScreen(
    navController: NavController,
    viewModel: SongArchiveViewModel = viewModel(
        factory = SongArchiveViewModelFactory(SongRepositoryImpl())
    )
) {
    val records by viewModel.records.collectAsState()

    // 현재 선택된 연습 기록 (플레이 카드로 띄울 대상)
    var selectedRecord by remember { mutableStateOf<PracticeRecord?>(null) }

    AppScreenContainer(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Song Archive",
            color = MainText,
            style = Typography.headlineSmall
        )

        Spacer(Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(records) { record ->
                SongArchivePreviewCard(
                    title = record.songTitle,
                    date = record.recordedDate,
                    imageUrl = record.albumImageUrl,
                    onClick = {
                        selectedRecord = record
                    }
                )
            }
        }

        // 가운데 떠 있는 플레이 카드
        selectedRecord?.let { record ->
            ArchivePlayerDialog(
                record = record,
                onDismiss = { selectedRecord = null },
                onRetry = {
                    selectedRecord = null
                    navController.navigate(
                        Screen.SongDetail.createRoute(record.songId)
                    )
                },
                onShowEvaluation = {
                    selectedRecord = null
                    navController.navigate(
                        Screen.AiEvaluation.createRoute(record.id)
                    )
                }
            )
        }
    }
}
