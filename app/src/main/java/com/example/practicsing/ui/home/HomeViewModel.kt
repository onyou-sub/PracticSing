package com.example.practicsing.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicsing.data.model.AiEvaluationResult
import com.example.practicsing.data.model.Song
import com.example.practicsing.data.repository.EvaluationRepository
import com.example.practicsing.data.source.sampleSongs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val hotSongs: List<Song> = emptyList(),                 // HOT SONGS 리스트
    val rankResults: List<AiEvaluationResult> = emptyList(),// RANK에 쓸 평가 결과들
    val selectedSongTab: String = "Weekly",                 // "Weekly" / "Monthly"
    val selectedRankTab: String = "Weekly",                 // "Weekly" / "Monthly"
    val isLoading: Boolean = false
)

class HomeViewModel : ViewModel() {

    private val evaluationRepository = EvaluationRepository()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // 캐싱용: Firestore에서 한 번 가져온 전체 평가 데이터
    private var allEvaluations: List<AiEvaluationResult> = emptyList()

    init {
        loadHomeData()
    }

    /** 최초 진입 시 한 번 Firestore에서 전체 평가 기록 로드 */
    private fun loadHomeData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // TODO: 필요하면 userId로 필터링해서 "현재 로그인 유저만" 보도록 바꿀 수도 있음
            allEvaluations = try {
                evaluationRepository.getAllEvaluations()
            } catch (e: Exception) {
                emptyList()
            }

            val defaultTab = "Weekly"

            val hot = buildHotSongs(period = defaultTab)
            val ranks = buildRankResults(period = defaultTab)

            _uiState.update {
                it.copy(
                    hotSongs = hot,
                    rankResults = ranks,
                    selectedSongTab = defaultTab,
                    selectedRankTab = defaultTab,
                    isLoading = false
                )
            }
        }
    }

    /** HOT SONGS 탭 변경 (Weekly / Monthly) */
    fun selectSongTab(tab: String) {
        val hot = buildHotSongs(period = tab)
        _uiState.update {
            it.copy(
                selectedSongTab = tab,
                hotSongs = hot
            )
        }
    }

    /** RANK 탭 변경 (Weekly / Monthly) */
    fun selectRankTab(tab: String) {
        val ranks = buildRankResults(period = tab)
        _uiState.update {
            it.copy(
                selectedRankTab = tab,
                rankResults = ranks
            )
        }
    }

    // -----------------------------
    // HOT SONGS 생성 로직
    // -----------------------------
    private fun buildHotSongs(period: String): List<Song> {
        if (allEvaluations.isEmpty()) {
            // 평가 데이터가 하나도 없으면 participants = 0인 기본 리스트 (혹은 빈 리스트)
            return sampleSongs
                .map { it.copy(participants = 0) }
                .take(5)
        }

        val startMillis = getPeriodStartMillis(period)
        val filtered = allEvaluations.filter { it.practicedAtMillis >= startMillis }

        // songId별 참여 횟수 카운트
        val countsBySongId: Map<String, Int> =
            filtered.groupBy { it.songId }.mapValues { (_, list) -> list.size }

        // sampleSongs를 베이스로, participants만 업데이트
        val enrichedSongs = sampleSongs.map { song ->
            val count = countsBySongId[song.id] ?: 0
            song.copy(participants = count)
        }

        // 참여자 수 기준 내림차순 정렬 → 상위 5개 정도만
        return enrichedSongs
            .sortedByDescending { it.participants }
            .take(5)
    }

    // -----------------------------
    // RANK 리스트 생성 로직
    // -----------------------------
    private fun buildRankResults(period: String): List<AiEvaluationResult> {
        if (allEvaluations.isEmpty()) return emptyList()

        val startMillis = getPeriodStartMillis(period)

        return allEvaluations
            .filter { it.practicedAtMillis >= startMillis }
            .sortedWith(
                compareByDescending<AiEvaluationResult> { it.score }
                    .thenByDescending { it.practicedAtMillis } // 점수 같으면 최신순
            )
            .take(5)
    }

    // -----------------------------
    // 기간별 시작 시점 계산
    // -----------------------------
    private fun getPeriodStartMillis(period: String): Long {
        val now = System.currentTimeMillis()
        val days = when (period) {
            "Weekly" -> 7
            "Monthly" -> 30
            else -> 7
        }
        val oneDayMillis = 24L * 60L * 60L * 1000L
        return now - days * oneDayMillis
    }
}
