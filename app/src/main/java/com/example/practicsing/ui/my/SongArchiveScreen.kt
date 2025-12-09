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
import com.example.practicsing.ui.common.RoundedBackButton

@Composable
fun SongArchiveScreen(
    navController: NavController,
    viewModel: SongArchiveViewModel = viewModel(
        factory = SongArchiveViewModelFactory(SongRepositoryImpl())
    )
) {
    val records by viewModel.records.collectAsState()
    var selectedRecord by remember { mutableStateOf<PracticeRecord?>(null) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(20.dp)
    ) {
        RoundedBackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp),
            onClick = { navController.popBackStack() }
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Song Archive",
                    color = MainText,
                    style = Typography.headlineSmall
                )
            }

            Spacer(Modifier.height(20.dp))


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(records) { record ->
                    SongArchivePreviewCard(
                        title = record.songTitle,
                        date = record.practicedDateText,
                        imageUrl = record.albumImageUrl,
                        onClick = {
                            selectedRecord = record
                        }
                    )
                }
            }
        }


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
