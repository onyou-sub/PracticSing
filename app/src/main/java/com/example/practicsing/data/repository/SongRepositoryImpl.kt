package com.example.practicsing.data.repository

import com.example.practicsing.data.model.Song
import com.example.practicsing.data.model.LeaderboardEntry
import com.example.practicsing.data.source.sampleSongs
import com.example.practicsing.data.source.sampleLeaderboard

class SongRepositoryImpl : SongRepository {

    override fun getSongs(): List<Song> = sampleSongs

    override fun getSongById(id: String): Song? =
        sampleSongs.find { it.id == id }

    override fun getLeaderboard(songId: String): List<LeaderboardEntry> =
        sampleLeaderboard.filter { it.songId == songId }
}
