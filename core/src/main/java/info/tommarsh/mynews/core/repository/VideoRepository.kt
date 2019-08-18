package info.tommarsh.mynews.core.repository

import info.tommarsh.mynews.core.Outcome
import info.tommarsh.mynews.core.errors.ErrorLiveData
import info.tommarsh.mynews.core.model.PlaylistModel

interface VideoRepository {
    val errors: ErrorLiveData

    suspend fun getPlaylist(): Outcome<PlaylistModel>
}