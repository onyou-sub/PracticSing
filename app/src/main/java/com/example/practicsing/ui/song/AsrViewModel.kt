package com.example.practicsing.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicsing.data.etri.*
import kotlinx.coroutines.launch
import android.util.Base64
import android.speech.tts.TextToSpeech
import java.util.Locale
import androidx.lifecycle.AndroidViewModel
import android.app.Application

import com.example.practicsing.BuildConfig


private val ACCESS_KEY = BuildConfig.ETRI_KEY

data class PronunciationResult(
    val recognized: String,
    val score: Float
)

class AsrViewModel(application: Application) : AndroidViewModel(application) {

    private val api = EtriApiService.create()
    private val recorder = RecordingManager()

    var isRecording by mutableStateOf(false)
    var resultText by mutableStateOf("result here")

    var pronunciationResult by mutableStateOf<PronunciationResult?>(null)

    private val buffer = mutableListOf<Byte>()

    private val context = application.applicationContext

    // TTS 객체
    private var tts: TextToSpeech? = null

    init {
        // TTS 초기화
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.KOREAN
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        tts?.stop()
        tts?.shutdown()
    }

    // 한국어 문장 읽기
    fun speakKorean(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
    fun startRecording() {
        buffer.clear()
        isRecording = true
        resultText = "recording..."

        recorder.startRecording { chunk ->
            buffer.addAll(chunk.toList())
        }
    }

    fun stopRecordingRecognition(accessKey: String) {
        recorder.stopRecording()
        isRecording = false

        resultText = "recognizing..."

        viewModelScope.launch {
            callRecognition(accessKey)
        }
    }

    fun stopRecordingPronunciation(accessKey: String, script: String) {
        recorder.stopRecording()
        isRecording = false

        resultText = "evaluating..."

        viewModelScope.launch {
            callPronunciation(accessKey, script)
        }
    }

    private suspend fun callRecognition(accessKey: String) {
        // 다시 작성 // 
    }

    private suspend fun callPronunciation(accessKey: String, script: String) {
        val base64 = Base64.encodeToString(buffer.toByteArray(), Base64.NO_WRAP)

        val req = EtriRequest(
            accessKey = accessKey,
            argument = EtriArgument(
                languageCode = "korean",
                script = script,
                audio = base64
            )
        )

        try {
            val res = api.pronunciation(req)
            val obj = res.returnObject

            pronunciationResult = PronunciationResult(
                recognized = obj?.recognized ?: "",
                score = obj?.score ?: 0f
            )
        } catch (e: Exception) {
            resultText = "오류: ${e.message}"
        }
    }


    //test audio file
    private fun loadAudioFromRaw(resId: Int): ByteArray {
        val inputStream = context.resources.openRawResource(resId)
        return inputStream.readBytes()
    }

    fun testPronunciationFromFile(resId: Int, script: String) {
        viewModelScope.launch {
            // 1) 파일 → byte array
            val audioBytes = loadAudioFromRaw(resId)

            // 2) 기존 buffer에 넣기
            buffer.clear()
            buffer.addAll(audioBytes.toList())

            // 3) callPronunciation 호출
            resultText = "evaluating..."
            callPronunciation(ACCESS_KEY, script)
        }
    }



}



