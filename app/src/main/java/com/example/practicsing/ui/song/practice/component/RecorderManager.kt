package com.example.practicsing.ui.song.practice.component

import android.content.Context
import android.media.MediaRecorder
import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class RecorderManager(private val context: Context) {

    private var recorder: MediaRecorder? = null
    private var outputFile: String? = null

    fun startRecording(): String {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "recording_$timestamp.mp4"

        val dir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val file = File(dir, fileName)
        outputFile = file.absolutePath

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFile)
            prepare()
            start()
        }

        return outputFile!!
    }

    fun stopRecording(): String? {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        return outputFile
    }
}
