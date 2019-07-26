package info.tommarsh.data

import androidx.lifecycle.LiveData
import info.tommarsh.core.Outcome
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.data.source.local.articles.ArticlesLocalDataStore
import info.tommarsh.data.source.remote.articles.ArticlesRemoteDataStore
import info.tommarsh.core.model.ArticleModel
import info.tommarsh.core.model.CategoryModel
import info.tommarsh.core.repository.ArticleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias TopicOutcome = Pair<String, Outcome<List<ArticleModel>>>

class ArticleDataRepository
@Inject internal constructor(
    private val local: ArticlesLocalDataStore,
    private val remote: ArticlesRemoteDataStore,
    override val errors: ErrorLiveData
) : ArticleRepository {

    override suspend fun getBreakingNews(source: String): LiveData<List<ArticleModel>> = local.getBreakingNews()

    override suspend fun refreshBreakingNews() {
        when (val networkItems = remote.getBreakingNews()) {
            is Outcome.Success -> local.saveBreakingNews(networkItems.data)
            is Outcome.Error -> errors.setError(networkItems.error)
        }
    }

    override suspend fun getFeed(): LiveData<List<ArticleModel>> = local.getFeed()

    override suspend fun searchArticles(query: String) = remote.searchArticles(query)

    override suspend fun refreshFeed(categories: List<CategoryModel>) = coroutineScope {
        launch { local.deleteUnselectedCategories() }
        articleProducer(categories).consumeEach { receiveArticles(it) }
    }

    private fun CoroutineScope.articleProducer(
        categories: List<CategoryModel>
    ) = produce {
        categories.forEach {
            send(produceArticles(it.id))
        }
    }

    private suspend fun produceArticles(id: String): TopicOutcome {
        return TopicOutcome(id, remote.getArticleForCategory(id))
    }

    private suspend fun receiveArticles(topic: TopicOutcome) {
        when (val outcome = topic.second) {
            is Outcome.Success -> local.saveCategory(topic.first, outcome.data)
            is Outcome.Error -> errors.setError(outcome.error)
        }
    }
}