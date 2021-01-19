package info.tommarsh.mynews.presentation.ui.top

import androidx.lifecycle.ViewModel
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.presentation.model.toViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TopNewsViewModel
@Inject constructor(
    repository: ArticleRepository,
    private val timeHelper: TimeHelper
) : ViewModel() {

    val articles = repository.getArticlesForCategory("general")
        .map { page -> page.map { model -> model.toViewModel(timeHelper) } }
}