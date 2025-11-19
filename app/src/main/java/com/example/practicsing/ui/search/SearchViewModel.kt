package com.example.practicsing.ui.search

import androidx.lifecycle.ViewModel
import com.example.practicsing.data.model.Song
import com.example.practicsing.data.repository.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel(
    private val songRepository: SongRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _recentQueries = MutableStateFlow<List<String>>(emptyList())
    val recentQueries = _recentQueries.asStateFlow()

    private val _results = MutableStateFlow<List<Song>>(emptyList())
    val results = _results.asStateFlow()

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

        val all = songRepository.getSongs()
        _results.value = all.filter {
            it.title.contains(q, ignoreCase = true) ||
                    it.artist.contains(q, ignoreCase = true)
        }
    }
}
