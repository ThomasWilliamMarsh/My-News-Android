package info.tommarsh.mynews.repository.source.remote.articles

import info.tommarsh.mynews.repository.model.remote.ArticlesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ArticleApiService {
    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(@Query("sources") sources: String = "bbc-news"): Response<ArticlesResponse>

    @GET("/v2/top-headlines")
    suspend fun getArticlesForCategory(@Query("category") category: String): Response<ArticlesResponse>

    @GET("/v2/everything")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("sources") sources: String = "bbc-news",
        @Query("sortBy") sortBy: String = "publishedAt"
    ): Response<ArticlesResponse>
}