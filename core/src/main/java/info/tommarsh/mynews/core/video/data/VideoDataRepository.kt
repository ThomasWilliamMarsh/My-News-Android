package info.tommarsh.mynews.core.video.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import info.tommarsh.mynews.core.video.data.remote.paging.VideoPagingSource
import info.tommarsh.mynews.core.video.data.remote.source.VideoRemoteDataStore
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VideoDataRepository
@Inject internal constructor(
    private val remote: VideoRemoteDataStore
) : VideoRepository {

    override fun getPlaylist(pageSize: Int): Flow<PagingData<PlaylistItemModel>> {
        return Pager(
            config = PagingConfig(pageSize),
            pagingSourceFactory = { VideoPagingSource(remote) }
        ).flow
    }
}