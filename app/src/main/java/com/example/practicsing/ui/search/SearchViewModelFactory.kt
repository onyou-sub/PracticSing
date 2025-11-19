package com.example.practicsing.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicsing.data.repository.SongRepository

class SearchViewModelFactory(
    private val songRepository: SongRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(songRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
