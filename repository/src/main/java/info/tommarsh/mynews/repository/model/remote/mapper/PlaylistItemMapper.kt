package info.tommarsh.mynews.repository.model.remote.mapper

import info.tommarsh.mynews.core.Mapper
import info.tommarsh.mynews.core.model.PlaylistItemModel
import info.tommarsh.mynews.repository.model.remote.PlaylistItemResponse
import javax.inject.Inject

class PlaylistItemMapper
@Inject constructor() :
    Mapper<PlaylistItemResponse, PlaylistItemModel> {
    override fun map(from: PlaylistItemResponse) = PlaylistItemModel(
        videoId = from.contentDetails?.videoId.orEmpty(),
        title = from.snippet?.title.orEmpty(),
        publishedAt = from.snippet?.publishedAt.orEmpty(),
        thumbnail = from.snippet?.thumbnails?.mediumRes?.url.orEmpty()
    )
}