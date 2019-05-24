package info.tommarsh.data.model.local.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.data.model.local.Category
import info.tommarsh.domain.model.CategoryModel
import javax.inject.Inject

class CategoryDataToDomainMapper
@Inject constructor() : Mapper<List<Category>, List<CategoryModel>> {
    override fun map(from: List<Category>) = from.map {
        CategoryModel(
            id = it.id,
            name = it.name,
            selected = it.selected
        )
    }
}