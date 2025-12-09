package com.example.practicsing.data.etri

import android.media.*
import android.util.Base64
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.atomic.AtomicBoolean

class EtriRecorderClient {
    private val sampleRate = 16000
    private var audioRecord: AudioRecord? = null
    private val isRecording = AtomicBoolean(false)
    private val speechData = ByteArrayOutputStream()
    private val TAG = "ETRI_CLIENT"

    fun startRecording() {
        if (isRecording.get()) return

        val bufferSize = AudioRecord.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )

        try {
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize
            )

            if (audioRecord?.state != AudioRecord.STATE_INITIALIZED) {
                Log.e(TAG, "Microphone init failed")
                return
            }

            audioRecord?.startRecording()
            isRecording.set(true)
            speechData.reset()

            Thread {
                val tempBuffer = ByteArray(bufferSize)
                while (isRecording.get()) {
                    val read = audioRecord?.read(tempBuffer, 0, bufferSize) ?: 0
                    if (read > 0) {
                        speechData.write(tempBuffer, 0, read)
                    }
                }
            }.start()

        } catch (e: Exception) {
            Log.e(TAG, "Recording error: ${e.message}")
            isRecording.set(false)
        }
    }

    suspend fun stopAndAnalyze(script: String): String = withContext(Dispatchers.IO) {
        isRecording.set(false)
        try {
            audioRecord?.stop()
            audioRecord?.release()
        } catch (e: Exception) {
            Log.e(TAG, "Stop error: ${e.message}")
        }
        audioRecord = null

        val pcmData = speechData.toByteArray()
        val wavData = pcmToWav(pcmData, sampleRate)

        val accessKey = "edd9bbfb-5938-454e-a4f8-99930ea98bc7"
        sendEtriRequest(wavData, accessKey, script)
    }
}

// PCM -> WAV encoder
fun pcmToWav(pcmData: ByteArray, sampleRate: Int, channels: Int = 1, bitsPerSample: Int = 16): ByteArray {
    val byteRate = sampleRate * channels * bitsPerSample / 8
    val totalSize = 44 + pcmData.size
    val out = ByteArrayOutputStream(totalSize)
    val header = ByteArray(44)

    header[0] = 'R'.code.toByte()
    header[1] = 'I'.code.toByte()
    header[2] = 'F'.code.toByte()
    header[3] = 'F'.code.toByte()

    val chunkSize = totalSize - 8
    header[4] = (chunkSize and 0xFF).toByte()
    header[5] = ((chunkSize shr 8) and 0xFF).toByte()
    header[6] = ((chunkSize shr 16) and 0xFF).toByte()
    header[7] = ((chunkSize shr 24) and 0xFF).toByte()

    header[8] = 'W'.code.toByte()
    header[9] = 'A'.code.toByte()
    header[10] = 'V'.code.toByte()
    header[11] = 'E'.code.toByte()

    header[12] = 'f'.code.toByte()
    header[13] = 'm'.code.toByte()
    header[14] = 't'.code.toByte()
    header[15] = ' '.code.toByte()

    header[16] = 16
    header[17] = 0
    header[18] = 0
    header[19] = 0

    header[20] = 1
    header[21] = 0

    header[22] = channels.toByte()
    header[23] = 0

    header[24] = (sampleRate and 0xFF).toByte()
    header[25] = ((sampleRate shr 8) and 0xFF).toByte()
    header[26] = ((sampleRate shr 16) and 0xFF).toByte()
    header[27] = ((sampleRate shr 24) and 0xFF).toByte()

    header[28] = (byteRate and 0xFF).toByte()
    header[29] = ((byteRate shr 8) and 0xFF).toByte()
    header[30] = ((byteRate shr 16) and 0xFF).toByte()
    header[31] = ((byteRate shr 24) and 0xFF).toByte()

    val blockAlign = channels * bitsPerSample / 8
    header[32] = blockAlign.toByte()
    header[33] = 0

    header[34] = bitsPerSample.toByte()
    header[35] = 0

    header[36] = 'd'.code.toByte()
    header[37] = 'a'.code.toByte()
    header[38] = 't'.code.toByte()
    header[39] = 'a'.code.toByte()

    val dataSize = pcmData.size
    header[40] = (dataSize and 0xFF).toByte()
    header[41] = ((dataSize shr 8) and 0xFF).toByte()
    header[42] = ((dataSize shr 16) and 0xFF).toByte()
    header[43] = ((dataSize shr 24) and 0xFF).toByte()

    out.write(header)
    out.write(pcmData)
    return out.toByteArray()
}

// Send WAV audio to ETRI server
fun sendEtriRequest(wavData: ByteArray, accessKey: String, script: String): String {
    val openApiURL = "http://epretx.etri.re.kr:8000/api/WiseASR_PronunciationKor"
    val languageCode = "korean"
    val audioBase64 = Base64.encodeToString(wavData, Base64.NO_WRAP)

    val requestBody = mapOf(
        "access_key" to accessKey,
        "argument" to mapOf(
            "language_code" to "korean",
            "script" to script,
            "audio" to audioBase64
        )
    )

    return try {
        val url = URL(openApiURL)
        val con = url.openConnection() as HttpURLConnection
        con.requestMethod = "POST"
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
        con.setRequestProperty("Authorization", accessKey) // <-- key in header
        con.doOutput = true

        val gson = Gson()
        DataOutputStream(con.outputStream).use {
            it.write(gson.toJson(requestBody).toByteArray(Charsets.UTF_8))
            it.flush()
        }

        if (con.responseCode == 200) {
            readStream(con.inputStream)
        } else {
            "Server error: ${con.responseCode}"
        }
    } catch (e: Exception) {
        "ETRI error: ${e.message}"
    }
}

// Read InputStream to String
private fun readStream(input: InputStream): String {
    val sb = StringBuilder()
    val br = BufferedReader(InputStreamReader(input))
    var line = br.readLine()
    while (line != null) {
        sb.append(line)
        line = br.readLine()
    }
    return sb.toString()
}
