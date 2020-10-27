package info.tommarsh.mynews.core.article.data.remote.source

import info.tommarsh.mynews.core.article.data.remote.model.ArticlesResponse
import info.tommarsh.mynews.core.di.NetworkModule.STANDARD_PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ArticleApiService {

    @GET("/v2/top-headlines")
    suspend fun getArticlesForCategory(
        @Query("category") category: String,
        @Query("page") page: Int = 0,
        @Query("pageSize") pageSize: Int = STANDARD_PAGE_SIZE,
        @Query("country") country: String = "gb"
    ): Response<ArticlesResponse>

    @GET("/v2/everything")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("page") page: Int = 0
    ): Response<ArticlesResponse>
}