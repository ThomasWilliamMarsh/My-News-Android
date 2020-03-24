package info.tommarsh.mynews.core.article.data

import info.tommarsh.mynews.core.article.data.local.source.ArticlesLocalDataStore
import info.tommarsh.mynews.core.article.data.remote.source.ArticlesRemoteDataStore
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.util.ErrorLiveData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleDataRepository
@Inject internal constructor(
    private val local: ArticlesLocalDataStore,
    private val remote: ArticlesRemoteDataStore,
    override val errors: ErrorLiveData
) : ArticleRepository {

    override fun getBreakingNews(source: String): Flow<List<ArticleModel>> = local.getBreakingNews()

    override fun getFeed(): Flow<List<ArticleModel>> = local.getFeed()

    override suspend fun refreshBreakingNews() {
        when (val items = remote.getBreakingNews()) {
            is Outcome.Success -> local.saveArticles(items.data)
            is Outcome.Error -> errors.setError(items.error)
        }
    }

    override suspend fun refreshFeed(categories: List<CategoryModel>) = coroutineScope {
        local.deleteUnselectedCategories()
        local.saveArticles(categories.fold(mutableListOf()) { result, category ->
            when (val outcome = remote.getArticleForCategory(category.id)) {
                is Outcome.Success -> result.apply {
                    outcome.data.forEach { it.category = category.id}
                    addAll(outcome.data)
                }
                is Outcome.Error -> {
                    errors.setError(outcome.error)
                    result
                }
            }
        })
    }

    override suspend fun searchArticles(query: String) = remote.searchArticles(query)
}