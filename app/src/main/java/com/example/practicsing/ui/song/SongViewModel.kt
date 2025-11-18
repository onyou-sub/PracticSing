package com.example.practicsing.ui.song

import androidx.lifecycle.ViewModel
import com.example.practicsing.data.model.Song
import com.example.practicsing.data.model.Genre
import com.example.practicsing.data.model.Level
import com.example.practicsing.data.repository.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SongViewModel(
    private val repo: SongRepository
) : ViewModel() {

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs = _songs.asStateFlow()

    private val _category = MutableStateFlow("HOT")
    val category = _category.asStateFlow()

    private val _selectedLevels = MutableStateFlow<Set<Level>>(emptySet())
    val selectedLevels = _selectedLevels.asStateFlow()

    init {
        loadSongs()
    }

    fun loadSongs() {
        val allSongs = repo.getSongs()

        var filtered = when (_category.value) {
            "HOT" -> allSongs.sortedByDescending { it.participants }
            "RECENT" -> allSongs.sortedByDescending { it.releaseDate }
            else -> allSongs.filter { it.genre.name.equals(_category.value, true) }
        }

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
