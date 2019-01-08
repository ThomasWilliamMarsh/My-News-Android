package info.tommarsh.presentation.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import info.tommarsh.domain.source.CategoryRepository
import info.tommarsh.presentation.model.CategoryViewModel
import info.tommarsh.presentation.model.mapper.CategoryDomainToViewModelMapper
import info.tommarsh.presentation.model.mapper.CategoryViewModelToDomainMapper
import info.tommarsh.presentation.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryChoiceViewModel
@Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val domainToViewModelmapper: CategoryDomainToViewModelMapper,
    private val viewModelToDomainMapper: CategoryViewModelToDomainMapper
) : BaseViewModel() {

    fun getCategories(): LiveData<List<CategoryViewModel>> =
        Transformations.map(categoryRepository.getCategories()) { model ->
            model.map { domainToViewModelmapper.map(it) }
        }

    fun updateCategory(category: CategoryViewModel) {
        launch(Dispatchers.IO) {
            val model = viewModelToDomainMapper.map(category)
            categoryRepository.updateCategory(model)
        }
    }
}