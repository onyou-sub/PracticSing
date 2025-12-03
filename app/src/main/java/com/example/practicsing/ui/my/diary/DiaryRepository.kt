package com.example.practicsing.ui.my.diary

import com.example.practicsing.data.model.Diary
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DiaryRepository(
    private val userId: String   // login id
) {

    private val db = FirebaseFirestore.getInstance()

    private fun diaryCollection() =
        db.collection("users")
            .document(userId)
            .collection("diary")


    // CREATE
    suspend fun addDiary(diary: Diary) {
        val newDoc = diaryCollection().document()
        val diaryWithId = diary.copy(id = newDoc.id)
        newDoc.set(diaryWithId).await()
    }

    // READ
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
