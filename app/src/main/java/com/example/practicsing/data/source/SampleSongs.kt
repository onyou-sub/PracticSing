package com.example.practicsing.data.source

import com.example.practicsing.data.model.Genre
import com.example.practicsing.data.model.Level
import com.example.practicsing.data.model.Song

val sampleSongs = listOf(
    Song(
        id = "drama_01",
        title = "Drama",
        artist = "aespa",
        genre = Genre.Dance,
        level = Level.Medium,
        description = "A dynamic dance track with powerful vocal lines.",
        imageUrl = "https://image.bugsm.co.kr/album/images/500/40929/4092948.jpg",
        releaseDate = "2023-11-10",
        participants = 34
    ),
    Song(
        id = "supernova_01",
        title = "Supernova",
        artist = "aespa",
        genre = Genre.POP,
        level = Level.Medium,
        description = "A catchy pop song with futuristic vibes.",
        imageUrl = "https://image.bugsm.co.kr/album/images/500/41011/4101141.jpg",
        releaseDate = "2024-05-01",
        participants = 41
    ),
    Song(
        id = "hypeboy_01",
        title = "Hype Boy",
        artist = "NewJeans",
        genre = Genre.POP,
        level = Level.Easy,
        description = "NewJeans’ signature minimal pop hit.",
        imageUrl = "https://image.bugsm.co.kr/album/images/500/40780/4078016.jpg",
        releaseDate = "2022-08-01",
        participants = 57
    ),
    Song(
        id = "eta_01",
        title = "ETA",
        artist = "NewJeans",
        genre = Genre.Acoustic,
        level = Level.Medium,
        description = "A bright and upbeat track with energetic rhythm.",
        imageUrl = "https://image.bugsm.co.kr/album/images/500/40885/4088574.jpg",
        releaseDate = "2023-07-21",
        participants = 29
    ),
)
