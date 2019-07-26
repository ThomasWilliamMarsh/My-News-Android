package info.tommarsh.presentation.model.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.core.TimeHelper
import info.tommarsh.core.model.PlaylistItemModel
import info.tommarsh.presentation.model.PlaylistItemViewModel
import javax.inject.Inject

class PlaylistItemViewModelMapper
@Inject constructor(private val timeHelper: TimeHelper) : Mapper<PlaylistItemModel, PlaylistItemViewModel> {
    override fun map(from: PlaylistItemModel) = PlaylistItemViewModel(
        videoId = from.videoId,
        title = from.title,
        publishedAt = timeHelper.timeBetween(isoString = from.publishedAt),
        thumbnail = from.thumbnail
    )
}