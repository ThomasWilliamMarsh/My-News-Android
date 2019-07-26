package info.tommarsh.data.source.remote.videos

import info.tommarsh.core.Outcome
import info.tommarsh.data.model.remote.mapper.PlaylistResponseMapper
import info.tommarsh.data.util.NetworkHelper
import info.tommarsh.core.model.PlaylistModel
import javax.inject.Inject

internal class VideoRemoteDataStore
@Inject constructor(
    private val api: VideoApiService,
    private val networkHelper: NetworkHelper,
    private val mapper: PlaylistResponseMapper
) {

    suspend fun getPlaylist(): Outcome<PlaylistModel> {
        return networkHelper.callApi(api.getPlaylistItems(), mapper)
    }
}