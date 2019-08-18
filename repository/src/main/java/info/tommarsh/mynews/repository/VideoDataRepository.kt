package info.tommarsh.mynews.repository

import info.tommarsh.mynews.core.errors.ErrorLiveData
import info.tommarsh.mynews.core.repository.VideoRepository
import info.tommarsh.mynews.repository.source.remote.videos.VideoRemoteDataStore
import javax.inject.Inject

class VideoDataRepository
@Inject internal constructor(
    private val remote: VideoRemoteDataStore,
    override val errors: ErrorLiveData
) : VideoRepository {
    override suspend fun getPlaylist() = remote.getPlaylist()
}