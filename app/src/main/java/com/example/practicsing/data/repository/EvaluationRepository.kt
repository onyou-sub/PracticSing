package com.example.practicsing.data.repository

import com.example.practicsing.data.model.AiEvaluationResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.Query
import android.util.Log

// 앱이 죽지 않게 보완

class EvaluationRepository {

    private val firestore = FirebaseFirestore.getInstance()

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
}