package info.tommarsh.presentation.ui.article.top

import androidx.lifecycle.liveData
import androidx.lifecycle.map
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import info.tommarsh.presentation.ui.common.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TopNewsViewModel
@Inject constructor(
    private val repository: ArticleRepository,
    private val mapper: ArticleViewModelMapper,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    val articles = liveData(context = coroutineContext) {
        val items = repository.getBreakingNews("").map(mapper::map)
        refreshBreakingNews()
        emitSource(items)
    }

    val errors = repository.errors

    fun refreshBreakingNews() {
        launch {
            withContext(dispatcherProvider.work()) {
                repository.refreshBreakingNews()
            }
        }
    }
}