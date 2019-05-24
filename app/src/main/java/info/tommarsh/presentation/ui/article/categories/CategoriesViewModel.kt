package info.tommarsh.presentation.ui.article.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.domain.model.CategoryModel
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.domain.source.CategoryRepository
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import info.tommarsh.presentation.ui.common.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoriesViewModel
@Inject constructor(
    private val articlesRepository: ArticleRepository,
    private val categoryRepository: CategoryRepository,
    private val mapper: ArticleViewModelMapper,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    val selectedCategories: LiveData<List<CategoryModel>> = categoryRepository.getSelectedCategories()

    val feed = categoryRepository.getSelectedCategories().switchMap {
        liveData(context = coroutineContext) {
            val items = articlesRepository.getFeed().map(mapper::map)
            emitSource(items)
        }
    }

    fun refreshFeed() {
        launch {
            withContext(dispatcherProvider.work()) {
                articlesRepository.refreshFeed(selectedCategories.value.orEmpty())
            }
        }
    }
}