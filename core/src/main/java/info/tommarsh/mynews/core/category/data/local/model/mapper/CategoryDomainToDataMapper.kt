package info.tommarsh.mynews.core.category.data.local.model.mapper

import info.tommarsh.mynews.core.category.data.local.model.Category
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.core.model.Mapper
import javax.inject.Inject

class CategoryDomainToDataMapper
@Inject constructor() :
    Mapper<List<CategoryModel>, List<Category>> {
    override fun map(from: List<CategoryModel>) = from.map {
        Category(
            id = it.id,
            name = it.name,
            selected = it.selected
        )
    }
}