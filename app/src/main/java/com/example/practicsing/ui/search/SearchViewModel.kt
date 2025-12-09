package com.example.practicsing.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicsing.data.model.Song
import com.example.practicsing.data.model.AiEvaluationResult
import com.example.practicsing.data.repository.EvaluationRepository
import com.example.practicsing.data.repository.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val songRepository: SongRepository,
    private val evaluationRepository: EvaluationRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _recentQueries = MutableStateFlow<List<String>>(emptyList())
    val recentQueries = _recentQueries.asStateFlow()

    private val _results = MutableStateFlow<List<Song>>(emptyList())
    val results = _results.asStateFlow()

    // 캐싱용: Firestore에서 가져온 전체 평가 데이터
    private var allEvaluations: List<AiEvaluationResult> = emptyList()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // 평가 데이터 로드 (HomeViewModel, SongViewModel 로직 참고)
            allEvaluations = try {
                evaluationRepository.getAllEvaluations()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        search(newQuery)
    }

    fun submitQuery() {
        val q = _query.value.trim()
        if (q.isBlank()) return

        // 최근 검색어에 추가 (중복은 위로 끌어올리기)
        _recentQueries.value =
            listOf(q) + _recentQueries.value.filterNot { it == q }
    }

    fun deleteRecent(item: String) {
        _recentQueries.value = _recentQueries.value.filterNot { it == item }
    }

    fun clearAllRecent() {
        _recentQueries.value = emptyList()
    }

    private fun search(q: String) {
        if (q.isBlank()) {
            _results.value = emptyList()
            return
        }

        // HomeViewModel/SongViewModel과 비슷하게 participant count 업데이트
        val startMillis = System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000 // 7 days (Weekly 기준)
        val relevantEvaluations = allEvaluations.filter { it.practicedAtMillis >= startMillis }
        val countsBySongId = relevantEvaluations.groupBy { it.songId }.mapValues { (_, list) -> list.size }

        val all = songRepository.getSongs().map { song ->
            val count = countsBySongId[song.id] ?: 0
            song.copy(participants = count)
        }

        _results.value = all.filter {
            it.title.contains(q, ignoreCase = true) ||
                    it.artist.contains(q, ignoreCase = true)
        }.sortedByDescending { it.participants } // 예: 검색 결과도 참여자 수 순으로 정렬하거나, 원래대로 유지 가능. 여기선 일단 정렬 추가함.
    }
}
