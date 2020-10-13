package info.tommarsh.mynews.presentation.ui.categories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.presentation.model.toCarousels
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriesViewModel
@ViewModelInject constructor(
    private val articlesRepository: ArticleRepository,
    private val categoryRepository: CategoryRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val timeHelper: TimeHelper
) : ViewModel() {

    val feed = articlesRepository.getFeed()
        .map { articles ->
            val selectedCategories = categoryRepository.getSelectedCategories()
            articles.toCarousels(selectedCategories, timeHelper)
        }
        .asLiveData(dispatcherProvider.work())

    fun refreshFeed() {
        viewModelScope.launch {
            withContext(dispatcherProvider.work()) {
                val selectedCategories = categoryRepository.getSelectedCategories()
                //articlesRepository.refreshFeed(selectedCategories)
            }
        }
    }
}