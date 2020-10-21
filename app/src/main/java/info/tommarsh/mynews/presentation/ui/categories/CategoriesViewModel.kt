package info.tommarsh.mynews.presentation.ui.categories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import androidx.paging.map
import info.tommarsh.mynews.categories.model.toViewModel
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.presentation.model.ArticleViewModel
import info.tommarsh.mynews.presentation.model.toViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(FlowPreview::class)
class CategoriesViewModel
@ViewModelInject constructor(
    private val articlesRepository: ArticleRepository,
    private val categoryRepository: CategoryRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val timeHelper: TimeHelper
) : ViewModel() {

    val selectedCategories = categoryRepository
        .getSelectedCategories()
        .map { domainModels -> domainModels.map { category -> category.toViewModel() } }
        .asLiveData(dispatcherProvider.work())

    fun getArticlesForCategory(category: String): Flow<PagingData<ArticleViewModel>> {
        return articlesRepository.getArticlesForCategory(category, FEED_PAGE_SIZE)
            .map { data -> data.map { page -> page.toViewModel(timeHelper) } }
    }

    companion object {
        private const val FEED_PAGE_SIZE = 5
    }
}