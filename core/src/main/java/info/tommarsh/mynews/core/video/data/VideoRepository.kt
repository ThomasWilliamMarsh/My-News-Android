package info.tommarsh.mynews.core.video.data

import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.util.ErrorLiveData
import info.tommarsh.mynews.core.video.domain.model.PlaylistModel

interface VideoRepository {
    val errors: ErrorLiveData

    suspend fun getPlaylist(): Outcome<PlaylistModel>
}