package info.tommarsh.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.core.Outcome
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchViewModel
@Inject constructor(
    private val repository: ArticleRepository,
    private val mapper: ArticleViewModelMapper,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _articles = MutableLiveData<List<ArticleViewModel>>()

    val articles: LiveData<List<ArticleViewModel>> = _articles

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