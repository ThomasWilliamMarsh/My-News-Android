package info.tommarsh.mynews.core.video.data

import info.tommarsh.mynews.core.util.ErrorLiveData
import info.tommarsh.mynews.core.video.data.remote.source.VideoRemoteDataStore
import javax.inject.Inject

class VideoDataRepository
@Inject internal constructor(
    private val remote: VideoRemoteDataStore,
    override val errors: ErrorLiveData
) : VideoRepository {
    override suspend fun getPlaylist() = remote.getPlaylist()
}