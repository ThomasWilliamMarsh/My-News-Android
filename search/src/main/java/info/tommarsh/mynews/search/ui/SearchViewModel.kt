package info.tommarsh.mynews.search.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.search.model.SearchItemViewModel
import info.tommarsh.mynews.search.model.toSearchViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SearchViewModel
@ViewModelInject constructor(
    private val repository: ArticleRepository,
    private val timeHelper: TimeHelper
) : ViewModel() {

    fun searchArticles(query: String): Flow<PagingData<SearchItemViewModel>> {
        return repository.searchArticles(query).map { page ->
            page.map { articleModel ->
                articleModel.toSearchViewModel(timeHelper)
            }
        }.cachedIn(viewModelScope)
    }
}