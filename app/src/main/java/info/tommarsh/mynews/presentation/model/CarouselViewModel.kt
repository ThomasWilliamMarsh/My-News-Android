package info.tommarsh.mynews.presentation.model

import info.tommarsh.mynews.core.model.ViewModel

data class CarouselViewModel(
    val name: String,
    val articles: List<ArticleViewModel>
) : ViewModel {
    override fun contentsTheSame(other: ViewModel): Boolean {
        return this == other
    }
}