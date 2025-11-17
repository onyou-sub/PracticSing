package com.example.practicsing.domain.usecase

import com.example.practicsing.data.model.Song
import com.example.practicsing.data.repository.FirebaseRepository
import kotlinx.coroutines.flow.Flow

class GetHotSongsUseCase(private val firebaseRepository: FirebaseRepository) {
    fun getWeeklyHotSongs(): Flow<List<Song>> = firebaseRepository.getWeeklyHotSongs()
    fun getMonthlyHotSongs(): Flow<List<Song>> = firebaseRepository.getMonthlyHotSongs()
}
