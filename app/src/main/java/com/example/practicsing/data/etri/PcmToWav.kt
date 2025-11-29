package com.example.practicsing.data.etri

import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

object PcmToWav {

    fun pcmToWav(pcmData: ByteArray, sampleRate: Int = 16000, channels: Int = 1): ByteArray {
        val byteRate = sampleRate * channels * 2  // 16bit = 2 bytes
        val wavData = ByteArrayOutputStream()

        // WAV Header (44 bytes)
        wavData.write("RIFF".toByteArray())
        wavData.write(intToLittleEndian(36 + pcmData.size)) // file size
        wavData.write("WAVE".toByteArray())

        wavData.write("fmt ".toByteArray())
        wavData.write(intToLittleEndian(16)) // subchunk1 size
        wavData.write(shortToLittleEndian(1)) // audio format (PCM)
        wavData.write(shortToLittleEndian(channels.toShort()))
        wavData.write(intToLittleEndian(sampleRate))
        wavData.write(intToLittleEndian(byteRate))
        wavData.write(shortToLittleEndian((channels * 2).toShort())) // block align
        wavData.write(shortToLittleEndian(16.toShort())) // bits per sample

        wavData.write("data".toByteArray())
        wavData.write(intToLittleEndian(pcmData.size))
        wavData.write(pcmData)

        return wavData.toByteArray()
    }

    private fun intToLittleEndian(value: Int): ByteArray =
        ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array()

    private fun shortToLittleEndian(value: Short): ByteArray =
        ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(value).array()
}
