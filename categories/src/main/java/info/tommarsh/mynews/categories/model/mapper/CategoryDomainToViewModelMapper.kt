package info.tommarsh.mynews.categories.model.mapper

import info.tommarsh.mynews.categories.model.CategoryViewModel
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.core.model.Mapper
import javax.inject.Inject

class CategoryDomainToViewModelMapper
@Inject constructor() :
    Mapper<List<CategoryModel>, List<CategoryViewModel>> {
    override fun map(from: List<CategoryModel>) = from.map {
        CategoryViewModel(
            id = it.id,
            name = it.name,
            selected = it.selected
        )
    }
}