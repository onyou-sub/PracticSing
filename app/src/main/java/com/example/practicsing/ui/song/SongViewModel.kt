package com.example.practicsing.ui.song

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicsing.data.model.Song
import com.example.practicsing.data.model.Genre
import com.example.practicsing.data.model.Level
import com.example.practicsing.data.model.AiEvaluationResult
import com.example.practicsing.data.repository.EvaluationRepository
import com.example.practicsing.data.repository.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SongViewModel(
    private val repo: SongRepository,
    private val evaluationRepository: EvaluationRepository
) : ViewModel() {

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs = _songs.asStateFlow()

    private val _category = MutableStateFlow("HOT")
    val category = _category.asStateFlow()

    private val _selectedLevels = MutableStateFlow<Set<Level>>(emptySet())
    val selectedLevels = _selectedLevels.asStateFlow()

    // 캐싱용: Firestore에서 가져온 전체 평가 데이터
    private var allEvaluations: List<AiEvaluationResult> = emptyList()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // 평가 데이터 로드 (HomeViewModel 로직 참고)
            allEvaluations = try {
                evaluationRepository.getAllEvaluations()
            } catch (e: Exception) {
                emptyList()
            }
            loadSongs()
        }
    }

    fun loadSongs() {
        val allSongs = repo.getSongs()

        // 1. 카테고리에 따른 필터링/정렬
        var filtered = when (_category.value) {
            "HOT" -> {
                // HomeViewModel과 동일한 로직: 최근 7일(혹은 전체) 평가 데이터 기반으로 participants 계산
                // 여기서는 "HOT"의 기준을 Home과 동일하게 최근 평가 데이터 수로 잡을지, 전체 누적으로 잡을지 결정해야 함
                // HomeViewModel은 period 파라미터가 있지만 여기는 없음. 기본적으로 "Weekly" 기준(7일)을 적용하거나 전체를 적용할 수 있음.
                // 사용자 요청: "make the search result get the data from the same place as the home hot songs part"
                // HomeViewModel은 buildHotSongs(period)를 사용함. 여기서도 비슷한 로직을 적용.
                // 편의상 Home의 "Weekly" 기준으로 7일간 데이터를 사용해 participants를 계산하여 정렬한다고 가정.
                
                val startMillis = System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000 // 7 days
                val relevantEvaluations = allEvaluations.filter { it.practicedAtMillis >= startMillis }
                
                val countsBySongId = relevantEvaluations.groupBy { it.songId }.mapValues { (_, list) -> list.size }

                allSongs.map { song ->
                    val count = countsBySongId[song.id] ?: 0
                    song.copy(participants = count)
                }.sortedByDescending { it.participants }
            }
            "RECENT" -> allSongs.sortedByDescending { it.releaseDate }
            else -> allSongs.filter { it.genre.name.equals(_category.value, true) }
        }

        // 2. 레벨 필터링
        if (_selectedLevels.value.isNotEmpty()) {
            filtered = filtered.filter { _selectedLevels.value.contains(it.level) }
        }

        _songs.value = filtered
    }

    fun selectCategory(category: String) {
        _category.value = category
        loadSongs()
    }

    fun toggleLevel(level: Level) {
        val mutable = _selectedLevels.value.toMutableSet()
        if (mutable.contains(level)) mutable.remove(level)
        else mutable.add(level)
        _selectedLevels.value = mutable
        loadSongs()
    }
}
