package info.tommarsh.mynews.home.ui.top

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.home.mappers.ArticlePageMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TopNewsViewModel
@Inject internal constructor(
    repository: ArticleRepository,
    private val pageMapper: ArticlePageMapper
) : ViewModel() {

    val articles = repository.getArticlesForCategory("general")
        .map { pageMapper.map(it) }
}