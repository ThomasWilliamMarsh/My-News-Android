package info.tommarsh.presentation.ui.article.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TopNewsViewModel
@Inject constructor(
    private val repository: ArticleRepository,
    private val mapper: ArticleViewModelMapper
) : ViewModel(), CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val errors = repository.errors

    init {
        refreshBreakingNews()
    }

    fun getBreakingNews(): LiveData<List<ArticleViewModel>> {
        return Transformations.map(repository.getBreakingNews("")) { domain ->
            domain.map { mapper.map(it) }
        }
    }

    private fun refreshBreakingNews() {
        launch {
            withContext(Dispatchers.IO) {
                repository.refreshBreakingNews()
            }
        }
    }
}