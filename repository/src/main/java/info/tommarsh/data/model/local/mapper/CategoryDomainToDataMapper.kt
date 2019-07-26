package info.tommarsh.data.model.local.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.data.model.local.Category
import info.tommarsh.core.model.CategoryModel
import javax.inject.Inject

class CategoryDomainToDataMapper
@Inject constructor() : Mapper<List<CategoryModel>, List<Category>> {
    override fun map(from: List<CategoryModel>) = from.map {
        Category(
            id = it.id,
            name = it.name,
            selected = it.selected
        )
    }
}