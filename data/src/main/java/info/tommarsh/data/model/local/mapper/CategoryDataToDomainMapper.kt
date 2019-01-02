package info.tommarsh.data.model.local.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.data.model.local.Category
import info.tommarsh.domain.model.CategoryModel
import javax.inject.Inject

class CategoryDataToDomainMapper
@Inject constructor() : Mapper<Category, CategoryModel> {
    override fun map(from: Category) = CategoryModel(
        id = from.id,
        name = from.name,
        selected = from.selected
    )
}