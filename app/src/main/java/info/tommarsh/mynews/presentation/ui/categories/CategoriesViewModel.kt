package info.tommarsh.mynews.presentation.ui.categories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.presentation.addHeaders
import info.tommarsh.mynews.presentation.model.toViewModel
import kotlinx.coroutines.flow.flatMapLatest
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

    val selectedCategories = categoryRepository.getSelectedCategoriesStream().asLiveData()

    val feed = categoryRepository.getSelectedCategoriesStream().flatMapLatest {
        articlesRepository.getFeed()
            .onStart { refreshFeed() }
            .map { domainModels -> domainModels.map { model -> model.toViewModel(timeHelper) } }
            .map { viewModels -> viewModels.addHeaders() }
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