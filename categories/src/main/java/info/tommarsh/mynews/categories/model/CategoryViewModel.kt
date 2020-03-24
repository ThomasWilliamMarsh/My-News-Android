package info.tommarsh.mynews.categories.model

import info.tommarsh.mynews.core.model.ViewModel

data class CategoryViewModel(
    val id: String,
    val name: String,
    var selected: Boolean
) : ViewModel {

    override fun contentsTheSame(other: ViewModel): Boolean {
        return this == other
    }
}