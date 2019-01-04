package info.tommarsh.presentation.model.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.domain.model.CategoryModel
import info.tommarsh.presentation.model.CategoryViewModel
import javax.inject.Inject

class CategoryViewModelToDomainMapper
@Inject constructor() : Mapper<CategoryViewModel, CategoryModel> {
    override fun map(from: CategoryViewModel) = CategoryModel(
        id = from.id,
        name = from.name,
        selected = from.selected
    )
}