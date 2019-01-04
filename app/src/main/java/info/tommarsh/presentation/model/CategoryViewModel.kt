package info.tommarsh.presentation.model

import info.tommarsh.core.ViewModel

data class CategoryViewModel(
    val id: String,
    val name: String,
    var selected: Boolean
) : ViewModel