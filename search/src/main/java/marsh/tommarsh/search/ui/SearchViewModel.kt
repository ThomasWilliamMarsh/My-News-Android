package marsh.tommarsh.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.core.Outcome
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.core.repository.ArticleRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import marsh.tommarsh.search.model.SearchItemViewModel
import marsh.tommarsh.search.model.mapper.SearchItemViewModelMapper
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