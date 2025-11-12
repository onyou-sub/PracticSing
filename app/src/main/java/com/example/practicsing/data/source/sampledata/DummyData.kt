package com.example.practicsing.data.source

import com.example.practicsing.data.model.Song
import com.example.practicsing.data.model.Rank

val weeklySongsDummy = listOf(
    Song("Drama", "aespa", "Dance", 34),
    Song("Supernova", "aespa", "Pop", 41),
    Song("Hype Boy", "NewJeans", "Pop", 57),
    Song("ETA", "NewJeans", "R&B", 29)
)

val monthlySongsDummy = listOf(
    Song("Love Dive", "IVE", "Pop", 63),
    Song("UNFORGIVEN", "LE SSERAFIM", "Dance", 45),
    Song("Magnetic", "ILLIT", "Pop", 50)
)

val weeklyRanksDummy = listOf(
    Rank("닉네임1", "봄날", "BTS", score = "80"),
    Rank("닉네임2", "Love Dive", "IVE", score = "80"),
    Rank("닉네임3", "Drama", "aespa", score = "80")
)

val monthlyRanksDummy = listOf(
    Rank("닉네임A", "Supernova", "aespa",score = "80"),
    Rank("닉네임B", "ETA", "NewJeans",score = "80"),
    Rank("닉네임C", "Magnetic", "ILLIT",score = "80")
)