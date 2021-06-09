package info.tommarsh.mynews.core.video.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import info.tommarsh.mynews.core.model.Resource
import info.tommarsh.mynews.core.video.data.remote.source.VideoRemoteDataStore
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel

class VideoPagingSource internal constructor(private val remote: VideoRemoteDataStore) :
    PagingSource<String, PlaylistItemModel>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PlaylistItemModel> {
        val page = params.key

        return when (val resource = remote.getPlaylist(page)) {
            is Resource.Data -> {
                val response = resource.data
                LoadResult.Page(
                    data = response.items,
                    nextKey = response.nextPageToken,
                    prevKey = null
                )
            }
            is Resource.Error -> LoadResult.Error(throwable = resource.error)
        }
    }

    override fun getRefreshKey(state: PagingState<String, PlaylistItemModel>) = ""
}