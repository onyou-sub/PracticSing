package com.example.practicsing.data.model

/**
 * 사용자가 어떤 곡을 한 번 부른 기록 + 그 위에 붙은 AI 평가 결과.
 */
data class PracticeRecord(
    val id: String,                 // 녹음/세션 id
    val songId: String,             // Song.id
    val songTitle: String,
    val artist: String,
    val albumImageUrl: String,

    val recordingUrl: String,       // 내가 부른 녹음 (url/local path)
    val durationText: String,       // "3:44"
    val recordedDate: String,       // "2024.03.04" 이런 형식

    // AI 평가 (아직 없으면 null)
    val aiScore: Int?,
    val aiStrengthComment: String?,
    val aiImprovementComment: String?
)
