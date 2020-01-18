package info.tommarsh.mynews.core.video.domain.model

import info.tommarsh.mynews.core.model.DomainModel

data class PlaylistItemModel(
    val videoId: String,
    val title: String,
    val publishedAt: String,
    val thumbnail: String
) : DomainModel