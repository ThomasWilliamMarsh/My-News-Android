package info.tommarsh.presentation.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.domain.source.CategoryRepository
import info.tommarsh.presentation.model.CategoryViewModel
import info.tommarsh.presentation.model.mapper.CategoryDomainToViewModelMapper
import info.tommarsh.presentation.ui.common.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryChoiceViewModel
@Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val domainToViewModelmapper: CategoryDomainToViewModelMapper,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    val categories: LiveData<List<CategoryViewModel>> = liveData(context = coroutineContext) {
        val items = categoryRepository.getCategories().map(domainToViewModelmapper::map)
        emitSource(items)
    }

    fun updateCategory(category: CategoryViewModel) = with(category) {
        launch {
            categoryRepository.updateCategory(id, selected)
        }
    }
}