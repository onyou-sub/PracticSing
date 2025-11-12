package com.example.practicsing.presentation.home

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.practicsing.data.model.Rank
import com.example.practicsing.data.model.Song
import com.example.practicsing.data.source.monthlyRanksDummy
import com.example.practicsing.data.source.monthlySongsDummy
import com.example.practicsing.data.source.weeklyRanksDummy
import com.example.practicsing.data.source.weeklySongsDummy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// UI 상태를 정의하는 데이터 클래스
data class HomeUiState(
    val hotSongs: List<Song> = emptyList(),
    val ranking: List<Rank> = emptyList(),
    val selectedSongTab: String = "Weekly",
    val selectedRankTab: String = "Weekly",
    val isLoading: Boolean = false
)

class HomeViewModel(/* repository나 useCase를 주입받을 수 있음 */) : ViewModel() {

    // UI에서 관찰할 수 있는 상태
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // 초기 데이터 로드 (실제로는 UseCase를 통해 비동기로 로드)
        loadHomeData()
    }

    private fun loadHomeData() {
        // ViewModel 초기화 시 주간 노래 및 랭킹으로 초기 상태 설정
        _uiState.update {
            it.copy(
                hotSongs = weeklySongsDummy,
                ranking = weeklyRanksDummy
            )
        }
    }

    fun selectSongTab(tab: String) {
        // 탭 변경 로직
        val songs = when (tab) {
            "Weekly" -> weeklySongsDummy
            "Monthly" -> monthlySongsDummy
            else -> emptyList()
        }
        _uiState.update {
            it.copy(
                selectedSongTab = tab,
                hotSongs = songs
            )
        }
    }

    fun selectRankTab(tab: String) {
        // 탭 변경 로직
        val ranks = when (tab) {
            "Weekly" -> weeklyRanksDummy
            "Monthly" -> monthlyRanksDummy
            else -> emptyList()
        }
        _uiState.update {
            it.copy(
                selectedRankTab = tab,
                ranking = ranks
            )
        }
    }
}