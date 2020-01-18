package info.tommarsh.mynews.core.video.data.remote.source

import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.util.NetworkHelper
import info.tommarsh.mynews.core.video.data.remote.model.mapper.PlaylistResponseMapper
import info.tommarsh.mynews.core.video.domain.model.PlaylistModel
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