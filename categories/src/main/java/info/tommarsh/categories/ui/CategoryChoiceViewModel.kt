package info.tommarsh.categories.ui

import androidx.lifecycle.*
import info.tommarsh.core.repository.CategoryRepository
import kotlinx.coroutines.launch
import info.tommarsh.categories.model.CategoryViewModel
import info.tommarsh.categories.model.mapper.CategoryDomainToViewModelMapper
import javax.inject.Inject

class CategoryChoiceViewModel
@Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val domainToViewModelmapper: CategoryDomainToViewModelMapper
) : ViewModel() {

    val categories: LiveData<List<CategoryViewModel>> = liveData {
        val items = categoryRepository.getCategories().map(domainToViewModelmapper::map)
        emitSource(items)
    }

    fun updateCategory(category: CategoryViewModel) = with(category) {
        viewModelScope.launch {
            categoryRepository.updateCategory(id, selected)
        }
    }
}