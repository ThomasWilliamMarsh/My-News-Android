package info.tommarsh.mynews.categories.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import info.tommarsh.mynews.categories.model.CategoryViewModel
import info.tommarsh.mynews.categories.model.toViewModel
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CategoryChoiceViewModel
@ViewModelInject constructor(
    private val categoryRepository: CategoryRepository,
    dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val categories = categoryRepository.getCategories()
        .map { domainModels -> domainModels.map { model -> model.toViewModel() } }
        .asLiveData(dispatcherProvider.main())

    fun updateCategory(category: CategoryViewModel) = with(category) {
        viewModelScope.launch {
            categoryRepository.updateCategory(id, selected)
        }
    }
}