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
import android.util.Log

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
            // 1) PCM → WAV 변환
            val pcm = buffer.toByteArray()
            val wav = PcmToWav.pcmToWav(pcm)  // <<< 추가됨

            // 2) Base64 인코딩
            val base64 = Base64.encodeToString(wav, Base64.NO_WRAP)

            // 3) ETRI 요청
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
            try {
                val wavData = loadAudioFromRaw(resId)
                Log.d("ASR", "wavData size = ${wavData.size}")

                val pcmData = wavData.copyOfRange(44, wavData.size)
                Log.d("ASR", "pcmData size = ${pcmData.size}")

                val newWav = PcmToWav.pcmToWav(pcmData)

                val base64 = Base64.encodeToString(newWav, Base64.NO_WRAP)
                Log.d("ASR", "base64 length = ${base64.length}")

                val req = EtriRequest(
                    accessKey = ACCESS_KEY,
                    argument = EtriArgument(
                        languageCode = "korean",
                        script = script,
                        audio = base64
                    )
                )

                resultText = "evaluating..."
                val res = api.pronunciation(req)

                val obj = res.returnObject
                Log.d("ASR", "API returnObject = $obj")

                pronunciationResult = PronunciationResult(
                    recognized = obj?.recognized ?: "",
                    score = obj?.score ?: 0f
                )

            } catch (e: Exception) {
                Log.e("ASR", "API 오류", e)
                resultText = "오류: ${e.localizedMessage}"
            }
        }
    }



}



