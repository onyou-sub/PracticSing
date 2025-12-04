package com.example.practicsing.ui.song.Test.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TTSManager(context: Context) : TextToSpeech.OnInitListener {

    private val tts: TextToSpeech = TextToSpeech(context, this)
    private var isReady = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            try {
                tts.language = Locale.KOREAN
                isReady = true
                Log.d("TTSManager", "TTS initialized successfully")
            } catch (e: Exception) {
                Log.e("TTSManager", "Language setting failed: ${e.message}")
            }
        } else {
            Log.e("TTSManager", "TTS initialization failed, status: $status")
        }
    }

    fun speak(text: String) {
        if (!isReady) {
            Log.e("TTSManager", "speak() called before TTS is ready")
            return
        }

        try {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts_speak_id")
        } catch (e: Exception) {
            Log.e("TTSManager", "Speak failed: ${e.message}")
        }
    }

    fun setPitch(pitch: Float) {
        if (isReady) {
            tts.setPitch(pitch)
        }
    }

    fun setSpeechRate(rate: Float) {
        if (isReady) {
            tts.setSpeechRate(rate)
        }
    }

    fun stop() {
        if (isReady) {
            tts.stop()
        }
    }

    fun shutdown() {
        try {
            tts.stop()
            tts.shutdown()
        } catch (e: Exception) {
            Log.e("TTSManager", "Shutdown failed: ${e.message}")
        }
    }
}