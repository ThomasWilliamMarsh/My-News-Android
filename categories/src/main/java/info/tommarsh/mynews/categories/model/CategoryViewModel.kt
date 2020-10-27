package info.tommarsh.mynews.categories.model

import info.tommarsh.mynews.core.model.ViewModel

data class CategoryViewModel(
    val id: String,
    val name: String,
    val selected: Boolean
) : ViewModel