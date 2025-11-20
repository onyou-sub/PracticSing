package com.example.practicsing.ui.pract


import android.Manifest
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import androidx.annotation.RequiresPermission
import kotlin.concurrent.thread
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt

data class NotePitch(
    val name: String,
    val frequency: Float, // detected pitch
    val targetHz: Float,  // expected pitch for this note
    val octave: Int = 4
)

class PitchDetector(
    private val sampleRate: Int = 44100,
    private val bufferSize: Int = 2048,
    private val onPitchDetected: (Float) -> Unit
) {
    private var audioRecord: AudioRecord? = null
    private var running = false

    // Microphone start
    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    fun start() {
        if (running) return
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            maxOf(AudioRecord.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            ), bufferSize)
        )
        audioRecord?.startRecording()
        running = true

        thread(start = true) {
            val buffer = ShortArray(bufferSize)
            while (running) {
                val read = audioRecord?.read(buffer, 0, buffer.size) ?: 0
                if (read > 0) {
                    val floatBuffer = FloatArray(read) { i -> buffer[i] / 32768.0f }
                    val pitch = detectPitchYIN(floatBuffer, sampleRate)
                    if (pitch != -1f) onPitchDetected(pitch)
                }
            }
        }
    }

    // Start from pre-recorded buffer
    fun startFromBuffer(audioBuffer: FloatArray) {
        thread(start = true) {
            var offset = 0
            running = true

            // Convert stereo to mono if buffer size is even (assume stereo)
            val monoBuffer = if (audioBuffer.size % 2 == 0) stereoToMono(audioBuffer) else audioBuffer

            while (running && offset + bufferSize <= monoBuffer.size) {
                val chunk = monoBuffer.sliceArray(offset until offset + bufferSize)
                val pitch = detectPitchYIN(chunk, sampleRate)
                if (pitch != -1f) onPitchDetected(pitch)
                offset += bufferSize
                Thread.sleep((1000 * chunk.size / sampleRate).toLong()) // simulate real-time
            }
            running = false
        }
    }

    fun stereoToMono(buffer: FloatArray): FloatArray {
        val mono = FloatArray(buffer.size / 2)
        for (i in mono.indices) {
            mono[i] = (buffer[i * 2] + buffer[i * 2 + 1]) / 2f
        }
        return mono
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
            for (i in 0 until buffer.size - tau) {
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
    if (pitchInHz <= 0f) return null

    val noteNames = arrayOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")
    val a4 = 440.0f
    val semitoneRatio = 2.0.pow(1.0 / 12.0).toFloat()

    // Calculate semitones difference from A4
    val n = floor(12 * ln(pitchInHz / a4) / ln(2.0) + 0.5).toInt()

    // Frequency of the closest note
    val closestFreq = a4 * semitoneRatio.pow(n)

    // Determine note name and octave
    val noteIndex = (n + 9).mod(12) // A is index 9
    val octave = 4 + ((n + 9) / 12)

    return NotePitch(
        name = noteNames[noteIndex],
        frequency = pitchInHz,
        targetHz = closestFreq,
        octave = octave
    )
}


fun Float.format(digits: Int) = "%.${digits}f".format(this)