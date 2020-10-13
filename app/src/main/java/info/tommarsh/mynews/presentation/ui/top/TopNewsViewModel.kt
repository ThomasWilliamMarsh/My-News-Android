package info.tommarsh.mynews.presentation.ui.top

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.map
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.presentation.model.toViewModel
import kotlinx.coroutines.flow.map

class TopNewsViewModel
@ViewModelInject constructor(
    repository: ArticleRepository,
    dispatcherProvider: DispatcherProvider,
    private val timeHelper: TimeHelper
    ) : ViewModel() {

    val articles = repository.getBreakingNews()
        .map { page -> page.map { model -> model.toViewModel(timeHelper) } }
        .asLiveData(dispatcherProvider.work())
}