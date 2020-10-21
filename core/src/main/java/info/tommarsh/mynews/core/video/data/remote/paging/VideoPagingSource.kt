package info.tommarsh.mynews.core.video.data.remote.paging

import androidx.paging.PagingSource
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.video.data.remote.source.VideoRemoteDataStore
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel

internal class VideoPagingSource(private val remote: VideoRemoteDataStore) :
    PagingSource<String, PlaylistItemModel>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PlaylistItemModel> {
        val page = params.key

        return when (val outcome = remote.getPlaylist(page)) {
            is Outcome.Success -> {
                val response = outcome.data
                LoadResult.Page(
                    data = response.items,
                    nextKey = response.nextPageToken,
                    prevKey = null
                )
            }
            is Outcome.Error -> LoadResult.Error(throwable = outcome.error)
        }
    }
}