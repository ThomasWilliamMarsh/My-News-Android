package info.tommarsh.mynews.core.category.domain

import info.tommarsh.mynews.core.model.DomainModel

data class CategoryModel(
    val id: String,
    val name: String,
    val selected: Boolean
) : DomainModel