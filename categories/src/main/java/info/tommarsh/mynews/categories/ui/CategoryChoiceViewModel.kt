package info.tommarsh.mynews.categories.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import info.tommarsh.mynews.categories.model.CategoryViewModel
import info.tommarsh.mynews.categories.model.mapper.CategoryDomainToViewModelMapper
import info.tommarsh.mynews.core.coroutines.DispatcherProvider
import info.tommarsh.mynews.core.repository.CategoryRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryChoiceViewModel
@Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val domainToViewModelmapper: CategoryDomainToViewModelMapper,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val categories = categoryRepository.getCategories()
        .map { domainToViewModelmapper.map(it) }
        .asLiveData(dispatcherProvider.main())

    fun updateCategory(category: CategoryViewModel) = with(category) {
        viewModelScope.launch {
            categoryRepository.updateCategory(id, selected)
        }
    }
}