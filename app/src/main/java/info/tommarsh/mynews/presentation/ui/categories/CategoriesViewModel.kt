package info.tommarsh.mynews.presentation.ui.categories

import androidx.lifecycle.*
import info.tommarsh.mynews.core.coroutines.DispatcherProvider
import info.tommarsh.mynews.core.model.CategoryModel
import info.tommarsh.mynews.core.repository.ArticleRepository
import info.tommarsh.mynews.core.repository.CategoryRepository
import info.tommarsh.mynews.presentation.addHeaders
import info.tommarsh.mynews.presentation.model.mapper.ArticleViewModelMapper
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