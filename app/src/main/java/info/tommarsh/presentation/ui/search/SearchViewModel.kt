package info.tommarsh.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.core.network.Outcome
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import info.tommarsh.presentation.ui.common.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchViewModel
@Inject constructor(
    private val repository: ArticleRepository,
    private val mapper: ArticleViewModelMapper,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _articles = MutableLiveData<List<ArticleViewModel>>()

    val articles: LiveData<List<ArticleViewModel>> = _articles

    val errors = repository.errors

    fun searchArticles(query: String) {
        launch {
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