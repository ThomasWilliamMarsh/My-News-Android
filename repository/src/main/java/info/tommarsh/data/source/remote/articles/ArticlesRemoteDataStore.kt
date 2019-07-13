package info.tommarsh.data.source.remote.articles

import info.tommarsh.core.Outcome
import info.tommarsh.data.model.remote.mapper.ArticleResponseMapper
import info.tommarsh.data.util.NetworkHelper
import info.tommarsh.domain.model.ArticleModel
import javax.inject.Inject

internal class ArticlesRemoteDataStore
@Inject constructor(
    private val mapper: ArticleResponseMapper,
    private val networkHelper: NetworkHelper,
    private val api: ArticleApiService
) {

    suspend fun getBreakingNews(): Outcome<List<ArticleModel>> {
        return networkHelper.callApi(api.getBreakingNews(), mapper)
    }

    suspend fun searchArticles(query: String): Outcome<List<ArticleModel>> {
        return networkHelper.callApi(api.searchArticles(query), mapper)
    }

    suspend fun getArticleForCategory(category: String): Outcome<List<ArticleModel>> {
        return networkHelper.callApi(api.getArticlesForCategory(category), mapper)
    }
}