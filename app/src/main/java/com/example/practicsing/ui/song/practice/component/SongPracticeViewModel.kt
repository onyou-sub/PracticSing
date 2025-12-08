package com.example.practicsing.ui.song.practice.component

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicsing.data.model.PracticeRecord
import com.example.practicsing.data.model.Song
import com.example.practicsing.data.repository.SongRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class SongPracticeViewModel(
    private val repository: SongRepository
) : ViewModel() {

    private var recorderManager: RecorderManager? = null

    var recordedFilePath: String? = null
        private set

    fun startRecording(context: Context) {
        recorderManager = RecorderManager(context)
        recordedFilePath = recorderManager?.startRecording()
    }

    fun stopRecording(): String? {
        recordedFilePath = recorderManager?.stopRecording()
        return recordedFilePath
    }

    fun saveRecord(song: Song) {
        val path = recordedFilePath ?: return
        
        // Simple duration placeholder or you could calculate actual duration
        val record = PracticeRecord(
            id = UUID.randomUUID().toString(),
            songId = song.id,
            songTitle = song.title,
            artist = song.artist,
            albumImageUrl = song.imageUrl,
            recordingUrl = path,
            durationText = "00:00", // You might want to pass actual duration
            recordedDate = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(Date()),
            aiScore = null,
            aiStrengthComment = null,
            aiImprovementComment = null
        )
        repository.savePracticeRecord(record)
    }
}

class SongPracticeViewModelFactory(
    private val repository: SongRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongPracticeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SongPracticeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
