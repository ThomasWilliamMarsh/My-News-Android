package info.tommarsh.mynews.search.model

import info.tommarsh.mynews.core.model.ViewModel

data class SearchItemViewModel(
    val author: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val content: String,
    val url: String,
    val urlToImage: String,
    val category: String
) : ViewModel {

    override fun contentsTheSame(other: ViewModel): Boolean {
        return url == (other as? SearchItemViewModel)?.url
    }
}