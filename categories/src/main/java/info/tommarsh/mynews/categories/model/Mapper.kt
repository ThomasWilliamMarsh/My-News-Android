package info.tommarsh.mynews.categories.model

import info.tommarsh.mynews.core.category.domain.CategoryModel

internal fun CategoryModel.toViewModel() = CategoryViewModel(
    id = id,
    name = name,
    selected = selected
)