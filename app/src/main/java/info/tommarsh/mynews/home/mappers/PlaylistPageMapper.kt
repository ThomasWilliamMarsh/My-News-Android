package info.tommarsh.mynews.home.mappers

import androidx.paging.PagingData
import androidx.paging.map
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel
import info.tommarsh.mynews.home.model.PlaylistItemViewModel
import javax.inject.Inject

class PlaylistPageMapper @Inject constructor(
    private val timeHelper: TimeHelper
) {

    fun map(page: PagingData<PlaylistItemModel>): PagingData<PlaylistItemViewModel> {
        return page.map { model -> model.toViewModel(timeHelper) }
    }
}

internal fun PlaylistItemModel.toViewModel(timeHelper: TimeHelper) = PlaylistItemViewModel(
    videoId = videoId,
    title = title,
    publishedAt = timeHelper.timeBetween(isoString = publishedAt),
    thumbnail = thumbnail
)