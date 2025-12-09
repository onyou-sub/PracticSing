package com.example.practicsing.ui.song.practice.component

import android.content.Context
import androidx.lifecycle.ViewModel

class SongPracticeViewModel : ViewModel() {

    private var recorderManager: RecorderManager? = null

    private var recordingStartTime: Long? = null

    var recordedFilePath: String? = null
        private set

    /**
     * 🎤 녹음 시작
     */
    fun startRecording(context: Context) {
        recorderManager = RecorderManager(context)
        recordingStartTime = System.currentTimeMillis()

        recordedFilePath = recorderManager?.startRecording()
    }

    /**
     * 🎤 녹음 종료 → (파일경로, 녹음길이ms) 형태로 반환
     */
    fun stopRecording(): Pair<String?, Long> {
        val endTime = System.currentTimeMillis()
        val durationMs = endTime - (recordingStartTime ?: endTime)

        recordedFilePath = recorderManager?.stopRecording()

        return Pair(recordedFilePath, durationMs)
    }
}
