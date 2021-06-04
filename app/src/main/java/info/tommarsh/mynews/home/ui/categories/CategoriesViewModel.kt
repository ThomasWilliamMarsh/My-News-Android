package info.tommarsh.mynews.home.ui.categories

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import info.tommarsh.mynews.categories.model.toViewModel
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.home.mappers.ArticlePageMapper
import info.tommarsh.mynews.home.model.ArticleViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel
@Inject constructor(
    private val articlesRepository: ArticleRepository,
    private val articlePageMapper: ArticlePageMapper,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val selectedCategories = categoryRepository
        .getSelectedCategories()
        .map { domainModels -> domainModels.map { category -> category.toViewModel() } }

    fun getArticlesForCategory(category: String): Flow<PagingData<ArticleViewModel>> {
        return articlesRepository.getArticlesForCategory(category, FEED_PAGE_SIZE)
            .map { articlePageMapper.map(it) }
    }

    companion object {
        private const val FEED_PAGE_SIZE = 5
    }
}