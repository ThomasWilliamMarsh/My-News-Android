package info.tommarsh.data.model.local.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.data.model.local.Category
import info.tommarsh.domain.model.CategoryModel
import javax.inject.Inject

class CategoryDomainToDataMapper
@Inject constructor() : Mapper<CategoryModel, Category> {
    override fun map(from: CategoryModel) = Category(
        id = from.id,
        name = from.name,
        selected = from.selected
    )
}