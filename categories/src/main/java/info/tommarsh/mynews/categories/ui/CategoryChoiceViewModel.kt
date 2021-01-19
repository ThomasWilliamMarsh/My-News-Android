package info.tommarsh.mynews.categories.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.tommarsh.mynews.categories.model.CategoryViewModel
import info.tommarsh.mynews.categories.model.toViewModel
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryChoiceViewModel
@Inject constructor(
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