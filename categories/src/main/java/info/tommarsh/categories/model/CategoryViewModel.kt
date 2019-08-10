package info.tommarsh.categories.model

import info.tommarsh.core.ViewModel

data class CategoryViewModel(
    val id: String,
    val name: String,
    var selected: Boolean
) : ViewModel