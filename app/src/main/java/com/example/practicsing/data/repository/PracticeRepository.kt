package com.example.practicsing.data.repository

interface PracticeRepository {
    suspend fun getCurrentStreak(userId: String): Int
    suspend fun hasPracticedToday(userId: String): Boolean
    suspend fun registerPracticeDone(userId: String): Int
}
