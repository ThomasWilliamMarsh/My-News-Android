package info.tommarsh.presentation.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import info.tommarsh.domain.source.CategoryRepository
import info.tommarsh.presentation.model.CategoryViewModel
import info.tommarsh.presentation.model.mapper.CategoryViewModelMapper
import info.tommarsh.presentation.ui.base.BaseViewModel
import javax.inject.Inject

class CategoryChoiceViewModel
@Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val mapper: CategoryViewModelMapper
) : BaseViewModel() {

    fun getCategories(): LiveData<List<CategoryViewModel>> =
        Transformations.map(categoryRepository.getCategories()) { model ->
            model.map { mapper.map(it) }
        }
}