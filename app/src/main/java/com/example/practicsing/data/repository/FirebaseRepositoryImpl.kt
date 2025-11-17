package com.example.practicsing.data.repository

import com.example.practicsing.data.model.Song
import com.google.firebase.firestore.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.Collections.emptyList

class FirebaseRepositoryImpl : FirebaseRepository {

    private val db = FirebaseFirestore.getInstance()

    override fun getWeeklyHotSongs(): Flow<List<Song>> = callbackFlow {
        val listener = db.collection("weekly")
            .addSnapshotListener { snapshot: QuerySnapshot?, e: FirebaseFirestoreException? ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                val songs = snapshot?.toObjects(Song::class.java) ?: emptyList()
                trySend(songs)
            }
        awaitClose { listener.remove() }
    }

    override fun getMonthlyHotSongs(): Flow<List<Song>> = callbackFlow {
        val listener = db.collection("monthly")
            .addSnapshotListener { snapshot: QuerySnapshot?, e: FirebaseFirestoreException? ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                val songs = snapshot?.toObjects(Song::class.java) ?: emptyList()
                trySend(songs)
            }
        awaitClose { listener.remove() }
    }
}
