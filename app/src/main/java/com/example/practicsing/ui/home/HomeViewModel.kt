package com.example.practicsing.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicsing.data.model.Song
import com.example.practicsing.data.model.LeaderboardEntry
import com.example.practicsing.data.repository.SongRepository
import com.example.practicsing.data.source.sampleSongs
import com.example.practicsing.data.source.sampleLeaderboard
import com.example.practicsing.ui.common.HomeRankUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeUiState(
    val hotSongs: List<Song> = emptyList(),
    val ranking: List<HomeRankUi> = emptyList(),
    val selectedSongTab: String = "Weekly",
    val selectedRankTab: String = "Weekly",
    val isLoading: Boolean = false
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        // 초기값은 주간 기준
        val weeklySongs = sampleSongs.take(5) // 더미 기준: 앞 5개를 weekly로 가정
        val weeklyRanks = sampleLeaderboard.take(3) // 상위 3명 가정

        _uiState.update {
            it.copy(
                hotSongs = weeklySongs,
                ranking = toHomeRankUi(weeklyRanks)
            )
        }
    }

    fun selectSongTab(tab: String) {
        val songs = when (tab) {
            "Weekly" -> sampleSongs.shuffled().take(5) // 더미
            "Monthly" -> sampleSongs.shuffled().take(5)
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
        val ranks = when (tab) {
            "Weekly" -> sampleLeaderboard.shuffled().take(5)
            "Monthly" -> sampleLeaderboard.shuffled().take(5)
            else -> emptyList()
        }

        _uiState.update {
            it.copy(
                selectedRankTab = tab,
                ranking = toHomeRankUi(ranks)
            )
        }
    }

    /** LeaderboardEntry → HomeRankUi 로 변환 */
    private fun toHomeRankUi(entries: List<LeaderboardEntry>): List<HomeRankUi> {
        return entries.map { entry ->
            val song = sampleSongs.find { it.id == entry.songId }
            HomeRankUi(
                profileImage = entry.profileImage,
                userName = entry.userName,
                songTitle = song?.title ?: "Unknown Song",
                artist = song?.artist ?: "Unknown Artist",
                score = entry.score
            )
        }
    }
}
