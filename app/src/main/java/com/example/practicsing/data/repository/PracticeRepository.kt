package com.example.practicsing.data.repository

interface PracticeRepository {
    suspend fun getCurrentStreak(userId: String): Int
    suspend fun registerPracticeDone(userId: String): Int
}
