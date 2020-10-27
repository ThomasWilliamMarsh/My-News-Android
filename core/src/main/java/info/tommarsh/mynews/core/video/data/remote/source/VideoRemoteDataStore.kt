package info.tommarsh.mynews.core.video.data.remote.source

import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.util.NetworkHelper
import info.tommarsh.mynews.core.video.data.remote.model.toDomainModel
import info.tommarsh.mynews.core.video.domain.model.PlaylistModel
import javax.inject.Inject

internal class VideoRemoteDataStore
@Inject constructor(
    private val api: VideoApiService,
    private val networkHelper: NetworkHelper
) {

    suspend fun getPlaylist(page: String?): Outcome<PlaylistModel> {
        return try {
            val response = networkHelper.callApi { api.getPlaylistItems(page) }
            Outcome.Success(response.toDomainModel())
        } catch (throwable: NetworkException) {
            Outcome.Error(throwable)
        }
    }
}