package info.tommarsh.presentation.ui.article.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.domain.model.CategoryModel
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.domain.source.CategoryRepository
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import info.tommarsh.presentation.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
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

    fun getSelectedCategories(): LiveData<List<CategoryModel>> = categoryRepository.getSelectedCategories()

    fun getFeed(): LiveData<List<ArticleViewModel>> = Transformations.map(articlesRepository.getFeed()) { model ->
        model.map { mapper.map(it) }
    }

    fun refreshFeed(categories: List<CategoryModel>) {
        launch {
            withContext(dispatcherProvider.work()) { articlesRepository.refreshFeed(categories) }
        }
    }
}