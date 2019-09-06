package info.tommarsh.mynews.repository

import androidx.lifecycle.LiveData
import info.tommarsh.mynews.core.Outcome
import info.tommarsh.mynews.core.errors.ErrorLiveData
import info.tommarsh.mynews.core.model.ArticleModel
import info.tommarsh.mynews.core.model.CategoryModel
import info.tommarsh.mynews.core.repository.ArticleRepository
import info.tommarsh.mynews.repository.source.local.articles.ArticlesLocalDataStore
import info.tommarsh.mynews.repository.source.remote.articles.ArticlesRemoteDataStore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

typealias TopicOutcome = Pair<String, Outcome<List<ArticleModel>>>

class ArticleDataRepository
@Inject internal constructor(
    private val local: ArticlesLocalDataStore,
    private val remote: ArticlesRemoteDataStore,
    override val errors: ErrorLiveData
) : ArticleRepository {

    override suspend fun getBreakingNews(source: String): LiveData<List<ArticleModel>> =
        local.getBreakingNews()

    override suspend fun refreshBreakingNews() {
        when (val networkItems = remote.getBreakingNews()) {
            is Outcome.Success -> local.saveBreakingNews(networkItems.data)
            is Outcome.Error -> errors.setError(networkItems.error)
        }
    }

    override suspend fun getFeed(): LiveData<List<ArticleModel>> = local.getFeed()

    override suspend fun searchArticles(query: String) = remote.searchArticles(query)

    override suspend fun refreshFeed(categories: List<CategoryModel>) = coroutineScope {
        local.deleteUnselectedCategories()
        categories.asFlow()
            .map { TopicOutcome(it.id, remote.getArticleForCategory(it.id)) }
            .buffer()
            .collect {
                when (val outcome = it.second) {
                    is Outcome.Success -> local.saveCategory(it.first, outcome.data)
                    is Outcome.Error -> errors.setError(outcome.error)
                }
            }
    }
}