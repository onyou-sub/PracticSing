package com.example.practicsing.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PracticeRepositoryImpl : PracticeRepository {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("Users")

    // Returns current streak (default 1)
    override suspend fun getCurrentStreak(userId: String): Int {
        if (userId.isEmpty()) return 1
        
        return try {
            val snapshot = usersCollection.document(userId).get().await()
            if (snapshot.exists()) {
                // If the field doesn't exist, default to 1
                snapshot.getLong("currentStreakDay")?.toInt() ?: 1
            } else {
                1
            }
        } catch (e: Exception) {
            e.printStackTrace()
            1
        }
    }

    // Registers practice for today, updates Firebase, and returns the new streak
    override suspend fun registerPracticeDone(userId: String): Int {
        if (userId.isEmpty()) return 1

        val userRef = usersCollection.document(userId)
        
        return try {
            db.runTransaction { transaction ->
                val snapshot = transaction.get(userRef)
                
                // Read current values, default if missing
                val lastDay = snapshot.getLong("lastPracticeEpochDay") ?: -1L
                val currentStreak = snapshot.getLong("currentStreakDay")?.toInt() ?: 1
                
                // Calculate today's epoch day
                val millisPerDay = 24L * 60L * 60L * 1000L
                val today = System.currentTimeMillis() / millisPerDay
                
                val newStreak = when {
                    lastDay == today -> currentStreak           // Already practiced today
                    lastDay == today - 1 -> currentStreak + 1   // Consecutive day
                    else -> 1                                   // Streak broken
                }
                
                // Update Firebase
                transaction.update(userRef, "lastPracticeEpochDay", today)
                transaction.update(userRef, "currentStreakDay", newStreak)
                
                newStreak
            }.await()
        } catch (e: Exception) {
            e.printStackTrace()
            // In case of error, just return 1 or current local state if possible, 
            // but for now 1 is safe default to avoid crash
            1
        }
    }
}
