package info.tommarsh.presentation.ui.categories

import androidx.lifecycle.*
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.core.model.CategoryModel
import info.tommarsh.core.repository.ArticleRepository
import info.tommarsh.core.repository.CategoryRepository
import info.tommarsh.presentation.addHeaders
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoriesViewModel
@Inject constructor(
    private val articlesRepository: ArticleRepository,
    private val categoryRepository: CategoryRepository,
    private val mapper: ArticleViewModelMapper,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val selectedCategories: LiveData<List<CategoryModel>> = categoryRepository.getSelectedCategoriesStream()

    val feed = categoryRepository.getSelectedCategoriesStream().switchMap {
        liveData {
            val items = articlesRepository.getFeed()
                .map(mapper::map)
                .map { it.addHeaders() }
            emitSource(items)
        }
    }

    fun refreshFeed() {
        viewModelScope.launch {
            withContext(dispatcherProvider.work()) {
                val selectedCategories = categoryRepository.getSelectedCategories()
                articlesRepository.refreshFeed(selectedCategories)
            }
        }
    }
}