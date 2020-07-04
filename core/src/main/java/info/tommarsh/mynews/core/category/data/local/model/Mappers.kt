package info.tommarsh.mynews.core.category.data.local.model

import info.tommarsh.mynews.core.category.domain.CategoryModel

internal fun Category.toDomainModel() = CategoryModel(
    id = id,
    name = name,
    selected = selected
)

internal fun CategoryModel.toDataModel() = Category(
    id = id,
    name = name,
    selected = selected
)