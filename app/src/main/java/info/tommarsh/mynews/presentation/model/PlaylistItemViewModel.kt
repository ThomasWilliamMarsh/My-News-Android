package info.tommarsh.mynews.presentation.model

import info.tommarsh.mynews.core.model.ViewModel

data class PlaylistItemViewModel(
    val videoId: String,
    val title: String,
    val publishedAt: String,
    val thumbnail: String
) : ViewModel {

    override fun contentsTheSame(other: ViewModel): Boolean {
        return videoId == (other as? PlaylistItemViewModel)?.videoId
    }
}