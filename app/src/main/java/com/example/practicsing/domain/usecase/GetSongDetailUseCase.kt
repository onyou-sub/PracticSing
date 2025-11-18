package com.example.practicsing.domain.usecase

import com.example.practicsing.data.model.Song
import com.example.practicsing.data.repository.SongRepository

class GetSongDetailUseCase(private val repo: SongRepository) {
    operator fun invoke(id: String): Song? = repo.getSongById(id)
}
