package info.tommarsh.data.model.remote.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.data.model.remote.PlaylistItemResponse
import info.tommarsh.domain.model.PlaylistItemModel
import javax.inject.Inject

class PlaylistItemMapper
@Inject constructor() : Mapper<PlaylistItemResponse, PlaylistItemModel> {
    override fun map(from: PlaylistItemResponse) = PlaylistItemModel(
        videoId = from.contentDetails?.videoId.orEmpty(),
        title = from.snippet?.title.orEmpty(),
        publishedAt = from.snippet?.publishedAt.orEmpty(),
        thumbnail = from.snippet?.thumbnails?.mediumRes?.url.orEmpty()
    )
}