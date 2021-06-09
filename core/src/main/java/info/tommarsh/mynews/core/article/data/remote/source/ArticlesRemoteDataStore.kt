package info.tommarsh.mynews.core.article.data.remote.source

import info.tommarsh.mynews.core.article.data.remote.model.toDomainList
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Resource
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.NetworkHelper
import javax.inject.Inject

class ArticlesRemoteDataStore
@Inject internal constructor(
    private val networkHelper: NetworkHelper,
    private val api: ArticleApiService,
    private val preferencesRepository: PreferencesRepository
) {

    suspend fun searchArticles(page: Int = 1, query: String): Resource<List<ArticleModel>> {
        return try {
            val response = networkHelper.callApi {
                api.searchArticles(
                    query,
                    page = page
                )
            }
            Resource.Data(response.toDomainList())
        } catch (throwable: NetworkException) {
            Resource.Error(throwable)
        }
    }

    suspend fun getArticleForCategory(
        page: Int = 1,
        pageSize: Int,
        category: String
    ): Resource<List<ArticleModel>> {
        return try {
            val response = networkHelper.callApi {
                api.getArticlesForCategory(
                    page = page,
                    pageSize = pageSize,
                    category = category,
                    country = preferencesRepository.getCountry()
                )
            }
            Resource.Data(response.toDomainList())
        } catch (throwable: NetworkException) {
            Resource.Error(throwable)
        }
    }
}