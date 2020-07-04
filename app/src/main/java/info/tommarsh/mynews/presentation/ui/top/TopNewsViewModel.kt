package info.tommarsh.mynews.presentation.ui.top

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.presentation.model.toViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopNewsViewModel
@ViewModelInject constructor(
    private val repository: ArticleRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val timeHelper: TimeHelper

    ) : ViewModel() {

    val articles = repository.getBreakingNews()
        .onStart { refreshBreakingNews() }
        .map { domainModels -> domainModels.map { model -> model.toViewModel(timeHelper)  } }
        .asLiveData(dispatcherProvider.main())

    val errors = repository.errors

    fun refreshBreakingNews() {
        viewModelScope.launch {
            withContext(dispatcherProvider.work()) {
                repository.refreshBreakingNews()
            }
        }
    }
}