package com.example.practicsing.data.repository

import android.util.Log
import com.example.practicsing.data.model.AiEvaluationResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class EvaluationRepository {

    private val firestore = FirebaseFirestore.getInstance()

    /**
     * 특정 유저(userId)의 평가 히스토리 (My Page 용)
     */
    suspend fun getUserEvaluationHistory(userId: String): List<AiEvaluationResult> {
        return try {
            val snapshot = firestore.collection("Evaluations")
                .whereEqualTo("userId", userId)
                .orderBy("practicedAtMillis", Query.Direction.DESCENDING)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(AiEvaluationResult::class.java)
                    ?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            Log.e("EvaluationRepository", "getUserEvaluationHistory error", e)
            emptyList()  // 에러 나도 앱은 안 죽게
        }
    }

    /**
     * 최근 N일 동안의 전체 평가 데이터 (Home HOT SONGS / RANK 용)
     *
     * days = 7  → Weekly
     * days = 30 → Monthly
     */
    suspend fun getEvaluationsForLastDays(days: Int): List<AiEvaluationResult> {
        val now = System.currentTimeMillis()
        val from = now - days * 24L * 60L * 60L * 1000L

        return try {
            val snapshot = firestore.collection("Evaluations")
                .whereGreaterThanOrEqualTo("practicedAtMillis", from)
                .orderBy("practicedAtMillis", Query.Direction.DESCENDING)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(AiEvaluationResult::class.java)
                    ?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            Log.e("EvaluationRepository", "getEvaluationsForLastDays error", e)
            emptyList()
        }
    }

    // 🔹 Home 화면에서 쓰는: 전체 평가 (모든 유저 대상)
    suspend fun getAllEvaluations(): List<AiEvaluationResult> {
        return try {
            val snapshot = firestore.collection("Evaluations")
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(AiEvaluationResult::class.java)
                    ?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            Log.e("EvaluationRepository", "getAllEvaluations error", e)
            emptyList()
        }
    }

    suspend fun getSongLeaderboard(
        songId: String,
        limit: Int = 20
    ): List<AiEvaluationResult> {
        return try {
            val snapshot = firestore.collection("Evaluations")
                .whereEqualTo("songId", songId)
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(limit.toLong())
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(AiEvaluationResult::class.java)
                    ?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            Log.e("EvaluationRepository", "getSongLeaderboard error", e)
            emptyList()
        }
    }
}
