package info.tommarsh.mynews.core.video.domain.model

data class PlaylistModel(
    val nextPageToken: String,
    val prevPageToken: String,
    val items: List<PlaylistItemModel>
)