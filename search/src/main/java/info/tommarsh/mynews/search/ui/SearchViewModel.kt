package info.tommarsh.mynews.search.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.search.model.Action
import info.tommarsh.mynews.search.model.Event
import info.tommarsh.mynews.search.model.Event.*
import info.tommarsh.mynews.search.model.toSearchViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class SearchViewModel
@ViewModelInject constructor(
    private val repository: ArticleRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val timeHelper: TimeHelper
) : ViewModel() {

    private val _searchState = MutableLiveData<Event>()

    val searchState: LiveData<Event> = _searchState

    fun postAction(action: Action) {
        when(action) {
            is Action.Search -> searchArticles(action.query)
        }
    }

    private fun searchArticles(query: String) {
        viewModelScope.launch {
            _searchState.value = Loading
            _searchState.value = when (val outcome = getOutcome(query)) {
                is Outcome.Success -> FetchedResults(outcome.data.toSearchViewModel(timeHelper))
                is Outcome.Error -> Error(outcome.error)
            }
        }
    }

    private suspend fun getOutcome(query: String) = withContext(dispatcherProvider.work()) {
        repository.searchArticles(query)
    }
}