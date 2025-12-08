package com.example.practicsing.data.repository

import com.example.practicsing.data.model.Song
import com.example.practicsing.data.model.LeaderboardEntry
import com.example.practicsing.data.model.PracticeRecord

interface SongRepository {
    fun getSongs(): List<Song>
    fun getSongById(id: String): Song?
    fun getLeaderboard(songId: String): List<LeaderboardEntry>
    fun getPracticeRecords(): List<PracticeRecord>
    fun savePracticeRecord(record: PracticeRecord)
}
