package info.tommarsh.mynews.presentation.model.mapper

import info.tommarsh.mynews.core.model.Mapper
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel
import info.tommarsh.mynews.presentation.model.PlaylistItemViewModel
import javax.inject.Inject

class PlaylistItemViewModelMapper
@Inject constructor(private val timeHelper: TimeHelper) :
    Mapper<PlaylistItemModel, PlaylistItemViewModel> {
    override fun map(from: PlaylistItemModel) = PlaylistItemViewModel(
        videoId = from.videoId,
        title = from.title,
        publishedAt = timeHelper.timeBetween(isoString = from.publishedAt),
        thumbnail = from.thumbnail
    )
}