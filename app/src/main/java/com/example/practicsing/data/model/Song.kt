package com.example.practicsing.data.model

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val genre: Genre,
    val level: Level,
    val description: String,
    val imageUrl: String,
    val releaseDate: String,
    val participants: Int,
    val filename: String
)
