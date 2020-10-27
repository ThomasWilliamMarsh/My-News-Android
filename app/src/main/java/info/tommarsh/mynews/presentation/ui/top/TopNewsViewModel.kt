package info.tommarsh.mynews.presentation.ui.top

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.map
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.presentation.model.toViewModel
import kotlinx.coroutines.flow.map

class TopNewsViewModel
@ViewModelInject constructor(
    repository: ArticleRepository,
    private val timeHelper: TimeHelper
) : ViewModel() {

    val articles = repository.getArticlesForCategory("general")
        .map { page -> page.map { model -> model.toViewModel(timeHelper) } }
}