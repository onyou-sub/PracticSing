package com.example.practicsing.ui.song

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicsing.data.repository.EvaluationRepository
import com.example.practicsing.data.repository.SongRepository

class SongViewModelFactory(
    private val songRepository: SongRepository,
    private val evaluationRepository: EvaluationRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SongViewModel(songRepository, evaluationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
