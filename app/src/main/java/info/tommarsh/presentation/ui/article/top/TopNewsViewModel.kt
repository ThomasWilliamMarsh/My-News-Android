package info.tommarsh.presentation.ui.article.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TopNewsViewModel
@Inject constructor(
    private val repository: ArticleRepository,
    private val mapper: ArticleViewModelMapper,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val articles = liveData {
        val items = repository.getBreakingNews("").map(mapper::map)
        refreshBreakingNews()
        emitSource(items)
    }

    val errors = repository.errors

    fun refreshBreakingNews() {
        viewModelScope.launch {
            withContext(dispatcherProvider.work()) {
                repository.refreshBreakingNews()
            }
        }
    }
}