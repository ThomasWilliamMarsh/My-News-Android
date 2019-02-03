package info.tommarsh.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import info.tommarsh.core.network.Outcome
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import info.tommarsh.presentation.ui.common.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel
@Inject constructor(
    private val repository: ArticleRepository,
    private val mapper: ArticleViewModelMapper
) : BaseViewModel() {

    private val _articles = MutableLiveData<List<ArticleViewModel>>()

    val articles: LiveData<List<ArticleViewModel>> = _articles

    fun searchArticles(query: String) = launch {
        val outcome = repository.searchArticles(query)
        when (outcome) {
            is Outcome.Success -> _articles.postValue(outcome.data.map { mapper.map(it) })
            is Outcome.Error -> repository.errors.setError(outcome.error)
        }
    }

    fun getErrors() = repository.errors
}