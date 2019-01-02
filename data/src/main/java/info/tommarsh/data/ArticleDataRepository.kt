package info.tommarsh.data

import androidx.lifecycle.LiveData
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.core.network.Outcome
import info.tommarsh.data.source.local.articles.ArticlesLocalDataStore
import info.tommarsh.data.source.remote.articles.ArticlesRemoteDataStore
import info.tommarsh.domain.model.ArticleModel
import info.tommarsh.domain.source.ArticleRepository
import javax.inject.Inject

class ArticleDataRepository
@Inject constructor(
    private val local: ArticlesLocalDataStore,
    private val remote: ArticlesRemoteDataStore,
    override val errors: ErrorLiveData
) : ArticleRepository {

    override fun getArticlesForCategory(category: String) = emptyList<ArticleModel>()

    override fun getBreakingNews(source: String): LiveData<List<ArticleModel>> = local.getBreakingNews()

    override fun refreshBreakingNews() {
        val networkItems = remote.getBreakingNews()
        when (networkItems) {
            is Outcome.Success -> local.saveBreakingNews(networkItems.data)
            is Outcome.Error -> errors.setError(networkItems.error)
        }
    }

    override fun searchArticles(query: String) = remote.searchArticles(query)
}