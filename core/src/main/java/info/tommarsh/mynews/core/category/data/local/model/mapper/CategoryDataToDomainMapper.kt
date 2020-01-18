package info.tommarsh.mynews.core.category.data.local.model.mapper

import info.tommarsh.mynews.core.category.data.local.model.Category
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.core.model.Mapper
import javax.inject.Inject

class CategoryDataToDomainMapper
@Inject constructor() :
    Mapper<List<Category>, List<CategoryModel>> {
    override fun map(from: List<Category>) = from.map {
        CategoryModel(
            id = it.id,
            name = it.name,
            selected = it.selected
        )
    }
}