package info.tommarsh.mynews.presentation.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.presentation.addHeaders
import info.tommarsh.mynews.presentation.model.mapper.ArticleViewModelMapper
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
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

    val selectedCategories = categoryRepository.getSelectedCategoriesStream().asLiveData()

    val feed = categoryRepository.getSelectedCategoriesStream().flatMapLatest {
        articlesRepository.getFeed()
            .onStart { refreshFeed() }
            .map { mapper.map(it) }
            .map { it.addHeaders() }
    }.asLiveData(dispatcherProvider.main())

    fun refreshFeed() {
        viewModelScope.launch {
            withContext(dispatcherProvider.work()) {
                val selectedCategories = categoryRepository.getSelectedCategories()
                articlesRepository.refreshFeed(selectedCategories)
            }
        }
    }
}