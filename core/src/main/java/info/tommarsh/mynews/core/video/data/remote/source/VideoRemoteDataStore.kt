package info.tommarsh.mynews.core.video.data.remote.source

import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Resource
import info.tommarsh.mynews.core.util.NetworkHelper
import info.tommarsh.mynews.core.video.data.remote.model.toDomainModel
import info.tommarsh.mynews.core.video.domain.model.PlaylistModel
import javax.inject.Inject

class VideoRemoteDataStore
@Inject internal constructor(
    private val api: VideoApiService,
    private val networkHelper: NetworkHelper
) {

    suspend fun getPlaylist(page: String?): Resource<PlaylistModel> {
        return try {
            val response = networkHelper.callApi { api.getPlaylistItems(page) }
            Resource.Data(response.toDomainModel())
        } catch (throwable: NetworkException) {
            Resource.Error(throwable)
        }
    }
}