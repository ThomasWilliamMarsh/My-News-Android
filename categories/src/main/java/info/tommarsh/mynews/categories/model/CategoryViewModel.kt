package info.tommarsh.mynews.categories.model

import info.tommarsh.mynews.core.ViewModel

data class CategoryViewModel(
    val id: String,
    val name: String,
    var selected: Boolean
) : ViewModel