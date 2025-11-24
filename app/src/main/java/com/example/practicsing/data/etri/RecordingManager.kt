package com.example.practicsing.data.etri

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlinx.coroutines.*

class RecordingManager {

    private var recorder: AudioRecord? = null
    private var isRecording = false

    fun startRecording(onData: (ByteArray) -> Unit) {
        val sampleRate = 16000
        val bufferSize = AudioRecord.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )

        recorder = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
        )

        val buffer = ByteArray(bufferSize)
        isRecording = true

        recorder?.startRecording()

        CoroutineScope(Dispatchers.IO).launch {
            while (isRecording) {
                val read = recorder!!.read(buffer, 0, buffer.size)
                if (read > 0) {
                    onData(buffer.copyOf(read))
                }
            }
        }
    }

    fun stopRecording() {
        isRecording = false
        recorder?.stop()
        recorder?.release()
        recorder = null
    }
}
