/**
 * 사용자가 어떤 곡을 한 번 부른 기록 + 그 위에 붙은 AI 평가 결과.
 */
package com.example.practicsing.data.model

data class PracticeRecord(
    val id: String = "",
    val songId: String = "",
    val songTitle: String = "",
    val artist: String = "",
    val albumImageUrl: String = "",
    val recordingUrl: String = "",
    val durationText: String = "",
    val practicedAtMillis: Long = 0L,
    val practicedDateText: String = "",

    val aiScore: Int? = null,
    val aiStrengthComment: String? = null,
    val aiImprovementComment: String? = null
)

fun PracticeRecord.toAiEvaluationResult(userId: String): AiEvaluationResult {
    return AiEvaluationResult(
        id = id,
        userId = userId,
        songId = songId,
        songTitle = songTitle,
        artist = artist,
        albumImageUrl = albumImageUrl,
        recordingUrl = recordingUrl,
        practicedAtMillis = practicedAtMillis,
        practicedDateText = practicedDateText,
        durationText = durationText,
        score = aiScore ?: 0,
        strengthComment = aiStrengthComment ?: "",
        weaknessComment = aiImprovementComment ?: "",
        startPositionSec = 0,
        endPositionSec = durationText.replace("초", "").toIntOrNull() ?: 0
    )
}
