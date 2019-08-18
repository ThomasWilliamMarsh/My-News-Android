package info.tommarsh.mynews.core.model

data class PlaylistModel(
    val nextPageToken: String,
    val prevPageToken: String,
    val items: List<PlaylistItemModel>
)