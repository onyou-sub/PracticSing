package com.example.practicsing.data.repository

import com.example.practicsing.data.model.Song
import com.example.practicsing.data.model.LeaderboardEntry
import com.example.practicsing.data.model.PracticeRecord
import com.example.practicsing.data.source.sampleSongs
import com.example.practicsing.data.source.sampleLeaderboard

class SongRepositoryImpl : SongRepository {

    companion object {
        // Shared storage for practice records across all instances
        private val practiceRecords = mutableListOf<PracticeRecord>()
    }

    override fun getSongs(): List<Song> = sampleSongs

    override fun getSongById(id: String): Song? =
        sampleSongs.find { it.id == id }

    override fun getLeaderboard(songId: String): List<LeaderboardEntry> =
        sampleLeaderboard.filter { it.songId == songId }

    override fun getPracticeRecords(): List<PracticeRecord> {
        return practiceRecords.toList()
    }

    override fun savePracticeRecord(record: PracticeRecord) {
        practiceRecords.add(0, record) // Add to top
    }
}
