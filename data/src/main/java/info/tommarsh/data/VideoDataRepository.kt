package info.tommarsh.data

import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.data.source.remote.videos.VideoRemoteDataStore
import info.tommarsh.domain.source.VideoRepository
import javax.inject.Inject

class VideoDataRepository
@Inject constructor(
    private val remote: VideoRemoteDataStore,
    override val errors: ErrorLiveData
) : VideoRepository {
    override fun getPlaylist() = remote.getPlaylist()
}