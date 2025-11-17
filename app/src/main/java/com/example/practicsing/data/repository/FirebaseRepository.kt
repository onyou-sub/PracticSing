package com.example.practicsing.data.repository

import com.example.practicsing.data.model.Song
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    fun getWeeklyHotSongs(): Flow<List<Song>>
    fun getMonthlyHotSongs(): Flow<List<Song>>
}
