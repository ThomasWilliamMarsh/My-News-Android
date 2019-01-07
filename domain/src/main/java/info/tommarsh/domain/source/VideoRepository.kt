package info.tommarsh.domain.source

import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.core.network.Outcome
import info.tommarsh.domain.model.PlaylistModel

interface VideoRepository {
    val errors: ErrorLiveData

    fun getPlaylist(): Outcome<PlaylistModel>
}