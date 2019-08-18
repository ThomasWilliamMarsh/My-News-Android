package info.tommarsh.mynews.core.model

data class PlaylistItemModel(
    val videoId: String,
    val title: String,
    val publishedAt: String,
    val thumbnail: String
) : DomainModel