package info.tommarsh.mynews.repository

import info.tommarsh.mynews.core.Outcome
import info.tommarsh.mynews.core.errors.ErrorLiveData
import info.tommarsh.mynews.core.model.ArticleModel
import info.tommarsh.mynews.core.model.CategoryModel
import info.tommarsh.mynews.core.repository.ArticleRepository
import info.tommarsh.mynews.repository.source.local.articles.ArticlesLocalDataStore
import info.tommarsh.mynews.repository.source.remote.articles.ArticlesRemoteDataStore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

typealias TopicOutcome = Pair<String, Outcome<List<ArticleModel>>>

class ArticleDataRepository
@Inject internal constructor(
    private val local: ArticlesLocalDataStore,
    private val remote: ArticlesRemoteDataStore,
    override val errors: ErrorLiveData
) : ArticleRepository {

    override fun getBreakingNews(source: String): Flow<List<ArticleModel>> = local.getBreakingNews()

    override fun getFeed(): Flow<List<ArticleModel>> = local.getFeed()

    override suspend fun refreshBreakingNews() {
        val items = remote.getBreakingNews()
        when (items) {
            is Outcome.Success -> local.saveBreakingNews(items.data)
            is Outcome.Error -> errors.setError(items.error)
        }
    }

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

    override suspend fun searchArticles(query: String) = remote.searchArticles(query)
}