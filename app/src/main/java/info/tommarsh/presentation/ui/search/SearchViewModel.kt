package info.tommarsh.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import info.tommarsh.core.network.Outcome
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import info.tommarsh.presentation.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel
@Inject constructor(
    private val repository: ArticleRepository,
    private val mapper: ArticleViewModelMapper
) : BaseViewModel() {

    private val articles = MutableLiveData<List<ArticleViewModel>>()

    fun getArticlesObservable(): LiveData<List<ArticleViewModel>> = articles

    fun searchArticles(query: String) {
        launch(Dispatchers.IO) {
            val outcome = repository.searchArticles(query)
            when (outcome) {
                is Outcome.Success -> articles.postValue(outcome.data.map { mapper.map(it) })
                is Outcome.Error -> repository.errors.setError(outcome.error)
            }
        }
    }

    fun getErrors() = repository.errors
}