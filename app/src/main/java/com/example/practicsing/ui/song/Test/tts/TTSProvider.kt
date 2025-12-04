package com.example.practicsing.ui.song.Test.tts

import android.content.Context

object TTSProvider {

    private var ttsManager: TTSManager? = null

    /**
     * 앱 시작 시 Activity Context로 초기화해야 한다.
     */
    fun initialize(context: Context) {
        if (ttsManager == null) {
            ttsManager = TTSManager(context)
        }
    }

    /**
     * 텍스트를 읽는다.
     */
    fun speak(text: String) {
        ttsManager?.speak(text)
    }

    /**
     * TTS pitch 조절
     */
    fun setPitch(pitch: Float) {
        ttsManager?.setPitch(pitch)
    }

    /**
     * TTS 속도 조절
     */
    fun setSpeechRate(rate: Float) {
        ttsManager?.setSpeechRate(rate)
    }

    /**
     * 종료 시점에 반드시 정리
     */
    fun shutdown() {
        ttsManager?.shutdown()
        ttsManager = null
    }
}