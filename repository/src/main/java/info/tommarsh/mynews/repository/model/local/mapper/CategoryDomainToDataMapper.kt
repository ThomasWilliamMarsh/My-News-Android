package info.tommarsh.mynews.repository.model.local.mapper

import info.tommarsh.mynews.core.Mapper
import info.tommarsh.mynews.core.model.CategoryModel
import info.tommarsh.mynews.repository.model.local.Category
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