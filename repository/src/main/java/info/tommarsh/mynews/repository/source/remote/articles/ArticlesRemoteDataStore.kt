package info.tommarsh.mynews.repository.source.remote.articles

import info.tommarsh.mynews.core.Outcome
import info.tommarsh.mynews.core.model.ArticleModel
import info.tommarsh.mynews.repository.model.remote.mapper.ArticleResponseMapper
import info.tommarsh.mynews.repository.util.NetworkHelper
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