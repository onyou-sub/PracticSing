package com.example.practicsing.data.model

// 기본 값이 없어서 앱이 무한 로딩하는 문제
// 기본 데이터 넣어두기

data class AiEvaluationResult(
    val id: String = "",
    val userId: String = "",
    val songId: String = "",

    val songTitle: String = "",
    val artist: String = "",
    val albumImageUrl: String = "",
    val recordingUrl: String = "",

    val startPositionSec: Int = 0,
    val endPositionSec: Int = 0,

    val practicedAtMillis: Long = 0L,
    val practicedDateText: String = "",

    val durationText: String = "",
    val score: Int = 0,
    val strengthComment: String = "",
    val weaknessComment: String = ""
)
