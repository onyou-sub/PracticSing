package com.example.practicsing.ui.my.diary

import com.example.practicsing.data.model.Diary
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DiaryRepository {

    private val db = FirebaseFirestore.getInstance()
    private val userId = "TEST_USER"   // -> 로그인 기능 있으면 UID로 변경

    private fun diaryCollection() =
        db.collection("users").document(userId).collection("diary")

    // CREATE
    suspend fun addDiary(diary: Diary) {
        val newDoc = diaryCollection().document()
        val diaryWithId = diary.copy(id = newDoc.id)
        newDoc.set(diaryWithId).await()
    }

    // READ (모든 일기)
    suspend fun getAllDiary(): List<Diary> {
        return diaryCollection()
            .orderBy("date")
            .get()
            .await()
            .toObjects(Diary::class.java)
    }

    // UPDATE
    suspend fun updateDiary(diary: Diary) {
        diaryCollection()
            .document(diary.id)
            .set(diary)
            .await()
    }

    // DELETE
    suspend fun deleteDiary(id: String) {
        diaryCollection()
            .document(id)
            .delete()
            .await()
    }
}