package info.tommarsh.mynews.core.video.data

import androidx.paging.PagingData
import info.tommarsh.mynews.core.di.NetworkModule
import info.tommarsh.mynews.core.di.NetworkModule.STANDARD_PAGE_SIZE
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    fun getPlaylist(pageSize: Int = STANDARD_PAGE_SIZE): Flow<PagingData<PlaylistItemModel>>
}