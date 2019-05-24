package info.tommarsh.presentation.model.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.domain.model.CategoryModel
import info.tommarsh.presentation.model.CategoryViewModel
import javax.inject.Inject

class CategoryDomainToViewModelMapper
@Inject constructor() : Mapper<List<CategoryModel>, List<CategoryViewModel>> {
    override fun map(from: List<CategoryModel>) = from.map {
        CategoryViewModel(
            id = it.id,
            name = it.name,
            selected = it.selected
        )
    }
}