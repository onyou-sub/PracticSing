package com.example.practicsing.domain.usecase

import com.example.practicsing.data.model.Song
import com.example.practicsing.data.repository.SongRepository

class GetSongsUseCase(private val repo: SongRepository) {
    operator fun invoke(): List<Song> = repo.getSongs()
}
