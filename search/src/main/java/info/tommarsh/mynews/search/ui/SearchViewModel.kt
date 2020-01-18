package info.tommarsh.mynews.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.search.model.SearchItemViewModel
import info.tommarsh.mynews.search.model.mapper.SearchItemViewModelMapper
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchViewModel
@Inject constructor(
    private val repository: ArticleRepository,
    private val mapper: SearchItemViewModelMapper,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _articles = MutableLiveData<List<SearchItemViewModel>>()

    val articles: LiveData<List<SearchItemViewModel>> = _articles

    val errors = repository.errors

    fun searchArticles(query: String) {
        viewModelScope.launch {
            when (val outcome = getOutcome(query)) {
                is Outcome.Success -> _articles.postValue(mapper.map(outcome.data))
                is Outcome.Error -> repository.errors.setError(outcome.error)
            }
        }
    }

    private suspend fun getOutcome(query: String) = withContext(dispatcherProvider.work()) {
        repository.searchArticles(query)
    }
}