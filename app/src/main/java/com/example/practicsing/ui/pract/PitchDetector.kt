package com.example.practicsing.ui.pract


import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import kotlin.concurrent.thread
import kotlin.math.abs
import kotlin.math.sqrt

data class NotePitch(
    val name: String,
    val frequency: Float, // detected pitch
    val targetHz: Float,  // expected pitch for this note
    val octave: Int = 4
)

class PitchDetector(
    private val sampleRate: Int = 22050,
    private val bufferSize: Int = 2048,
    private val onPitchDetected: (Float) -> Unit
) {

    private var audioRecord: AudioRecord? = null
    private var running = false

    fun start() {
        if (running) return

        val minBufferSize = AudioRecord.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )

        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            maxOf(minBufferSize, bufferSize)
        )

        audioRecord?.startRecording()
        running = true

        thread(start = true) {
            val buffer = ShortArray(bufferSize)
            while (running) {
                // Read audio from microphone
                val read = audioRecord?.read(buffer, 0, buffer.size) ?: 0


                Log.d("PitchDebug", "Buffer: ${buffer.joinToString(", ")}")

                if (read > 0) {
                    val floatBuffer = FloatArray(read) { i -> buffer[i] / 32768.0f }
                    val pitch = detectPitchYIN(floatBuffer, sampleRate)
                    if (pitch != -1f) {
                        onPitchDetected(pitch)
                        Log.d("PitchDetector", "Pitch detected: $pitch Hz")
                    }
                }
            }
        }
    }

    fun stop() {
        running = false
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
    }

    private fun detectPitchYIN(buffer: FloatArray, sampleRate: Int): Float {
        val tauMax = buffer.size / 2
        val yinBuffer = FloatArray(tauMax)

        for (tau in 0 until tauMax) {
            var sum = 0f
            for (i in 0 until tauMax) {
                val delta = buffer[i] - buffer[i + tau]
                sum += delta * delta
            }
            yinBuffer[tau] = sum
        }

        var runningSum = 0f
        yinBuffer[0] = 1f
        for (tau in 1 until tauMax) {
            runningSum += yinBuffer[tau]
            yinBuffer[tau] *= tau / runningSum
        }

        val threshold = 0.15f
        var tauEstimate = -1
        for (tau in 2 until tauMax) {
            if (yinBuffer[tau] < threshold) {
                tauEstimate = tau
                break
            }
        }


        return if (tauEstimate != -1) sampleRate.toFloat() / tauEstimate else -1f
    }
}

fun processPitch(pitchInHz: Float): NotePitch? {
    return when (pitchInHz) {
        in 65.41f..69.30f -> NotePitch("C", pitchInHz, 65.41f, 2)
        in 69.31f..73.42f -> NotePitch("C#", pitchInHz, 69.30f, 2)
        in 73.43f..77.78f -> NotePitch("D", pitchInHz, 73.42f, 2)
        in 77.79f..82.41f -> NotePitch("D#", pitchInHz, 77.78f, 2)
        in 82.42f..87.31f -> NotePitch("E", pitchInHz, 82.41f, 2)
        in 87.32f..92.50f -> NotePitch("F", pitchInHz, 87.31f, 2)
        in 92.51f..98.00f -> NotePitch("F#", pitchInHz, 92.50f, 2)
        in 98.01f..103.83f -> NotePitch("G", pitchInHz, 98.00f, 2)
        in 103.84f..110.00f -> NotePitch("G#", pitchInHz, 103.83f, 2)
        in 110.01f..116.54f -> NotePitch("A", pitchInHz, 110.00f, 2)
        in 116.55f..123.47f -> NotePitch("A#", pitchInHz, 116.54f, 2)
        in 123.48f..130.81f -> NotePitch("B", pitchInHz, 123.47f, 2)

        in 130.82f..138.59f -> NotePitch("C", pitchInHz, 130.81f, 3)
        in 138.60f..146.83f -> NotePitch("C#", pitchInHz, 138.59f, 3)
        in 146.84f..155.56f -> NotePitch("D", pitchInHz, 146.83f, 3)
        in 155.57f..164.81f -> NotePitch("D#", pitchInHz, 155.56f, 3)
        in 164.82f..174.61f -> NotePitch("E", pitchInHz, 164.81f, 3)
        in 174.62f..185.00f -> NotePitch("F", pitchInHz, 174.61f, 3)
        in 185.01f..196.00f -> NotePitch("F#", pitchInHz, 185.00f, 3)
        in 196.01f..207.65f -> NotePitch("G", pitchInHz, 196.00f, 3)
        in 207.66f..220.00f -> NotePitch("G#", pitchInHz, 207.65f, 3)
        in 220.01f..233.08f -> NotePitch("A", pitchInHz, 220.00f, 3)
        in 233.09f..246.94f -> NotePitch("A#", pitchInHz, 233.08f, 3)
        in 246.95f..261.63f -> NotePitch("B", pitchInHz, 246.94f, 3)

        in 261.64f..277.18f -> NotePitch("C", pitchInHz, 261.63f, 4)
        in 277.19f..293.66f -> NotePitch("C#", pitchInHz, 277.18f, 4)
        in 293.67f..311.13f -> NotePitch("D", pitchInHz, 293.66f, 4)
        in 311.14f..329.63f -> NotePitch("D#", pitchInHz, 311.13f, 4)
        in 329.64f..349.23f -> NotePitch("E", pitchInHz, 329.63f, 4)
        in 349.24f..369.99f -> NotePitch("F", pitchInHz, 349.23f, 4)
        in 370.0f..392.0f -> NotePitch("F#", pitchInHz, 369.99f, 4)
        in 392.01f..415.30f -> NotePitch("G", pitchInHz, 392.00f, 4)
        in 415.31f..440.00f -> NotePitch("G#", pitchInHz, 415.30f, 4)
        in 440.01f..466.16f -> NotePitch("A", pitchInHz, 440.00f, 4)
        in 466.17f..493.88f -> NotePitch("A#", pitchInHz, 466.16f, 4)
        in 493.89f..523.25f -> NotePitch("B", pitchInHz, 493.88f, 4)

        // add higher octaves similarly if needed
        else -> null
    }
}

fun Float.format(digits: Int) = "%.${digits}f".format(this)