package info.tommarsh.mynews.core.video.data.remote.model.mapper

import info.tommarsh.mynews.core.video.data.remote.model.PlaylistItemResponse
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel
import javax.inject.Inject

class PlaylistItemMapper
@Inject constructor() :
    Mapper<PlaylistItemResponse, PlaylistItemModel> {
    override fun map(from: PlaylistItemResponse) =
        PlaylistItemModel(
            videoId = from.contentDetails?.videoId.orEmpty(),
            title = from.snippet?.title.orEmpty(),
            publishedAt = from.snippet?.publishedAt.orEmpty(),
            thumbnail = from.snippet?.thumbnails?.mediumRes?.url.orEmpty()
        )
}