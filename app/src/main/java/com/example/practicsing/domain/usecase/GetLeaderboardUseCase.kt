package com.example.practicsing.domain.usecase

import com.example.practicsing.data.model.LeaderboardEntry
import com.example.practicsing.data.repository.SongRepository

class GetLeaderboardUseCase(
    private val repo: SongRepository
) {
    operator fun invoke(songId: String): List<LeaderboardEntry> =
        repo.getLeaderboard(songId)
}
