package com.example.practicsing.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import android.app.Application
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class AsrViewModel(application: Application) : AndroidViewModel(application) {

    // --- Firestore main_lyrics 저장 ---
    private val _mainLyrics = mutableStateOf<List<String>>(emptyList())
    val mainLyrics: State<List<String>> = _mainLyrics

    /**
     * Firestore에서 main_lyrics 불러오기
     * songId는 Firestore 문서 이름
     */
    fun loadMainLyrics(songId: String) {
        val db = Firebase.firestore

        db.collection("lyrics")
            .document(songId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val lines = document.get("main_lyrics") as? List<String>
                    _mainLyrics.value = lines ?: emptyList()
                } else {
                    _mainLyrics.value = emptyList()
                }
            }
            .addOnFailureListener { e ->
                Log.e("AsrViewModel", "Firestore load failed", e)
                _mainLyrics.value = listOf("Failed to load main lyrics")
            }
    }
}