package com.example.practicsing.data.model

data class AiEvaluationResult(
    val songTitle: String,        // 노래 제목
    val artist: String,           // 아티스트
    val albumImageUrl: String,    // 앨범 커버 이미지
    val recordingUrl: String,     // 내가 부른 녹음 파일 URL or local path
    val durationText: String,     // "3:44" 이런 형식
    val score: Int,               // AI 점수 (0~100)
    val strengthComment: String,  // 좋은 점 피드백
    val weaknessComment: String   // 개선점 피드백
)
