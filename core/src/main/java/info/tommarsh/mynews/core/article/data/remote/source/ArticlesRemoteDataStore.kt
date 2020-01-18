package info.tommarsh.mynews.core.article.data.remote.source

import info.tommarsh.mynews.core.article.data.remote.model.mapper.ArticleResponseMapper
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.util.NetworkHelper
import javax.inject.Inject

internal class ArticlesRemoteDataStore
@Inject constructor(
    private val mapper: ArticleResponseMapper,
    private val networkHelper: NetworkHelper,
    private val api: ArticleApiService
) {

    suspend fun getBreakingNews(): Outcome<List<ArticleModel>> {
        return networkHelper.callApi(mapper) {
            api.getBreakingNews()
        }
    }

    suspend fun searchArticles(query: String): Outcome<List<ArticleModel>> {
        return networkHelper.callApi(mapper) {
            api.searchArticles(query)
        }
    }

    suspend fun getArticleForCategory(category: String): Outcome<List<ArticleModel>> {
        return networkHelper.callApi(mapper) {
            api.getArticlesForCategory(category)
        }
    }
}