package info.tommarsh.mynews.core.video.data.remote.model

import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel
import info.tommarsh.mynews.core.video.domain.model.PlaylistModel

internal fun PlaylistItemResponse.toDomainModel() = PlaylistItemModel(
    videoId = contentDetails?.videoId.orEmpty(),
    title = snippet?.title.orEmpty(),
    publishedAt = snippet?.publishedAt.orEmpty(),
    thumbnail = snippet?.thumbnails?.mediumRes?.url.orEmpty()
)

internal fun PlaylistResponse.toDomainModel() = PlaylistModel(
    nextPageToken = nextPageToken.orEmpty(),
    prevPageToken = prevPageToken.orEmpty(),
    items = items.map { playlistItem -> playlistItem.toDomainModel() }
)