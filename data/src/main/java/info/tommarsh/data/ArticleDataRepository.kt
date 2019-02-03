package info.tommarsh.data

import androidx.lifecycle.LiveData
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.core.network.Outcome
import info.tommarsh.data.source.local.articles.ArticlesLocalDataStore
import info.tommarsh.data.source.remote.articles.ArticlesRemoteDataStore
import info.tommarsh.domain.model.ArticleModel
import info.tommarsh.domain.model.CategoryModel
import info.tommarsh.domain.source.ArticleRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleDataRepository
@Inject constructor(
    private val local: ArticlesLocalDataStore,
    private val remote: ArticlesRemoteDataStore,
    override val errors: ErrorLiveData
) : ArticleRepository {

    override fun getBreakingNews(source: String): LiveData<List<ArticleModel>> = local.getBreakingNews()

    override fun refreshBreakingNews() {
        val networkItems = remote.getBreakingNews()
        when (networkItems) {
            is Outcome.Success -> local.saveBreakingNews(networkItems.data)
            is Outcome.Error -> errors.setError(networkItems.error)
        }
    }

    override fun getFeed(): LiveData<List<ArticleModel>> = local.getFeed()

    override fun searchArticles(query: String) = remote.searchArticles(query)

    override suspend fun refreshFeed(categories: List<CategoryModel>) = coroutineScope {
        val channel = Channel<Outcome<List<ArticleModel>>>()

        categories.forEach {
            launch { produceArticles(it.id, channel) }
        }

        repeat(categories.size) {
            receiveArticles(categories[it].id, channel.receive())
        }
    }

    private suspend fun produceArticles(id: String, channel: SendChannel<Outcome<List<ArticleModel>>>) {
        channel.send(remote.getArticleForCategory(id))
    }

    private fun receiveArticles(id: String, outcome: Outcome<List<ArticleModel>>) {
        when (outcome) {
            is Outcome.Success -> local.saveCategory(id, outcome.data)
            is Outcome.Error -> errors.setError(outcome.error)
        }
    }

}