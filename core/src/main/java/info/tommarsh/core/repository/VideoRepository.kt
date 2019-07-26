package info.tommarsh.core.repository

import info.tommarsh.core.Outcome
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.core.model.PlaylistModel

interface VideoRepository {
    val errors: ErrorLiveData

    suspend fun getPlaylist(): Outcome<PlaylistModel>
}