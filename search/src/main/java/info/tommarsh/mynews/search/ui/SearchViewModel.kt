package info.tommarsh.mynews.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.search.mappers.SearchItemPageMapper
import info.tommarsh.mynews.search.model.SearchItemViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject internal constructor(
    private val repository: ArticleRepository,
    private val searchItemPageMapper: SearchItemPageMapper
) : ViewModel() {

    fun searchArticles(query: String): Flow<PagingData<SearchItemViewModel>> {
        return repository.searchArticles(query).map { searchItemPageMapper.map(it) }
            .cachedIn(viewModelScope)
    }
}