package info.tommarsh.mynews.core.article.data.remote.source

import info.tommarsh.mynews.core.article.data.remote.model.toDomainList
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.NetworkHelper
import javax.inject.Inject

internal class ArticlesRemoteDataStore
@Inject constructor(
    private val networkHelper: NetworkHelper,
    private val preferences: PreferencesRepository,
    private val api: ArticleApiService
) {

    suspend fun getBreakingNews(page: Int = 0): Outcome<List<ArticleModel>> {
        return try {
            val response = networkHelper.callApi {
                api.getBreakingNews(
                    sources = preferences.getSources(),
                    page = page
                )
            }
            Outcome.Success(response.toDomainList())
        } catch (throwable: NetworkException) {
            Outcome.Error(throwable)
        }
    }

    suspend fun searchArticles(query: String): Outcome<List<ArticleModel>> {
        return try {
            val response = networkHelper.callApi { api.searchArticles(query) }
            Outcome.Success(response.toDomainList())
        } catch (throwable: NetworkException) {
            Outcome.Error(throwable)
        }
    }

    suspend fun getArticleForCategory(category: String): Outcome<List<ArticleModel>> {
        return try {
            val response = networkHelper.callApi { api.getArticlesForCategory(category) }
            Outcome.Success(response.toDomainList())
        } catch (throwable: NetworkException) {
            Outcome.Error(throwable)
        }
    }
}