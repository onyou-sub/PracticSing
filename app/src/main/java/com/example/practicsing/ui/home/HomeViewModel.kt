package com.example.practicsing.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicsing.data.model.Rank
import com.example.practicsing.data.model.Song
import com.example.practicsing.data.repository.FirebaseRepositoryImpl
import com.example.practicsing.data.source.sampledata.monthlyRanksDummy
import com.example.practicsing.data.source.sampledata.weeklyRanksDummy
import com.example.practicsing.domain.usecase.GetHotSongsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// UI 상태를 정의하는 데이터 클래스
data class HomeUiState(
    val hotSongs: List<Song> = emptyList(),
    val ranking: List<Rank> = emptyList(),
    val selectedSongTab: String = "Weekly",
    val selectedRankTab: String = "Weekly",
    val isLoading: Boolean = false,
    val error: String? = null
)

class HomeViewModel(
    private val getHotSongsUseCase: GetHotSongsUseCase = GetHotSongsUseCase(FirebaseRepositoryImpl())
) : ViewModel() {

    // UI에서 관찰할 수 있는 상태
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // 초기 데이터 로드
        loadHotSongs()
        // 랭킹은 일단 더미 데이터로 유지
        _uiState.update {
            it.copy(
                ranking = weeklyRanksDummy
            )
        }
    }

    private fun loadHotSongs() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val songFlow = when (_uiState.value.selectedSongTab) {
                "Weekly" -> getHotSongsUseCase.getWeeklyHotSongs()
                "Monthly" -> getHotSongsUseCase.getMonthlyHotSongs()
                else -> return@launch
            }

            songFlow.onEach { songs ->
                _uiState.update {
                    it.copy(
                        hotSongs = songs,
                        isLoading = false
                    )
                }
            }.catch { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }.launchIn(viewModelScope)
        }
    }

    fun selectSongTab(tab: String) {
        _uiState.update {
            it.copy(
                selectedSongTab = tab,
            )
        }
        loadHotSongs()
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
