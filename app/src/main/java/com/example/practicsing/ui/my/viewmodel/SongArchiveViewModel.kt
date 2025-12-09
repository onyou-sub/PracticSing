package com.example.practicsing.ui.my.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicsing.data.model.PracticeRecord
import com.example.practicsing.data.repository.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SongArchiveViewModel(
    private val repo: SongRepository
) : ViewModel() {

    // TODO: 실제로는 repo에서 PracticeRecord 목록 가져오도록 수정
    private val _records = MutableStateFlow(samplePracticeRecords())
    val records = _records.asStateFlow()
}

class SongArchiveViewModelFactory(
    private val songRepository: SongRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongArchiveViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SongArchiveViewModel(songRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// 임시 샘플 데이터
private fun samplePracticeRecords(): List<PracticeRecord> =
    listOf(
        PracticeRecord(
            id = "rec1",
            songId = "song_hypeboy",
            songTitle = "Without You",
            artist = "Emma Volka",
            albumImageUrl = "https://picsum.photos/600/300",
            recordingUrl = "",
            durationText = "3:44",
            practicedDateText = "2024.03.04",
            aiScore = 80,
            aiStrengthComment = "Your pronunciation is smooth and stable.",
            aiImprovementComment = "Try to emphasize the ending consonants more clearly."
        )
        // 필요하면 더 추가
    )
