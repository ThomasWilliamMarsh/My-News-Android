package info.tommarsh.presentation.ui.article.categories

import androidx.lifecycle.*
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.domain.model.CategoryModel
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.domain.source.CategoryRepository
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
            val items = articlesRepository.getFeed().map(mapper::map)
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