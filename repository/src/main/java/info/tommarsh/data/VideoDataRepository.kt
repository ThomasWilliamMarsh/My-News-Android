package info.tommarsh.data

import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.data.source.remote.videos.VideoRemoteDataStore
import info.tommarsh.core.repository.VideoRepository
import javax.inject.Inject

class VideoDataRepository
@Inject internal constructor(
    private val remote: VideoRemoteDataStore,
    override val errors: ErrorLiveData
) : VideoRepository {
    override suspend fun getPlaylist() = remote.getPlaylist()
}