package com.example.practicsing.data.repository

import com.example.practicsing.data.model.Song
import com.example.practicsing.data.model.LeaderboardEntry

interface SongRepository {
    fun getSongs(): List<Song>
    fun getSongById(id: String): Song?
    fun getLeaderboard(songId: String): List<LeaderboardEntry>
}
