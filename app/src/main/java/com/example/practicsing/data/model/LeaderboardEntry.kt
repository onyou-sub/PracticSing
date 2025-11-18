package com.example.practicsing.data.model

import java.time.LocalDate

data class LeaderboardEntry(
    val songId: String,
    val profileImage: String,
    val userName: String,
    val score: Int,
    val date: String
)
