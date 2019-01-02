package info.tommarsh.presentation.model.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.domain.model.CategoryModel
import info.tommarsh.presentation.model.CategoryViewModel
import javax.inject.Inject

class CategoryViewModelMapper
@Inject constructor() : Mapper<CategoryModel, CategoryViewModel> {
    override fun map(from: CategoryModel) = CategoryViewModel(
        id = from.id,
        name = from.name,
        selected = from.selected
    )
}