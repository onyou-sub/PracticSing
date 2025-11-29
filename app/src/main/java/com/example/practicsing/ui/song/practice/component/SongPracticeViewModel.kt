package com.example.practicsing.ui.song.practice.component

import android.content.Context
import androidx.lifecycle.ViewModel

class SongPracticeViewModel : ViewModel() {

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
}
