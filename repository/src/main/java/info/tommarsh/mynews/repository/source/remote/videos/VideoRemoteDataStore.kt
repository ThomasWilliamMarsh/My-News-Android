package info.tommarsh.mynews.repository.source.remote.videos

import info.tommarsh.mynews.core.Outcome
import info.tommarsh.mynews.core.model.PlaylistModel
import info.tommarsh.mynews.repository.model.remote.mapper.PlaylistResponseMapper
import info.tommarsh.mynews.repository.util.NetworkHelper
import javax.inject.Inject

internal class VideoRemoteDataStore
@Inject constructor(
    private val api: VideoApiService,
    private val networkHelper: NetworkHelper,
    private val mapper: PlaylistResponseMapper
) {

    suspend fun getPlaylist(): Outcome<PlaylistModel> {
        return networkHelper.callApi(mapper, api::getPlaylistItems)
    }
}