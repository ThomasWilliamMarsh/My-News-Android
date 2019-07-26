package info.tommarsh.presentation.ui.categories

import androidx.lifecycle.*
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.domain.source.CategoryRepository
import info.tommarsh.presentation.model.CategoryViewModel
import info.tommarsh.presentation.model.mapper.CategoryDomainToViewModelMapper
import kotlinx.coroutines.launch
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