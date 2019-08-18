package info.tommarsh.mynews.repository.model.local.mapper

import info.tommarsh.mynews.core.Mapper
import info.tommarsh.mynews.core.model.CategoryModel
import info.tommarsh.mynews.repository.model.local.Category
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