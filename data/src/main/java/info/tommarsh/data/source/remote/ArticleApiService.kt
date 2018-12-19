package info.tommarsh.data.source.remote

import info.tommarsh.data.model.remote.ArticlesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {
    @GET("/v2/top-headlines")
    fun getBreakingNews(@Query("sources") sources: String = "bbc-news"): Call<ArticlesResponse>

    @GET("/v2/top-headlines")
    fun getCategory(@Query("category") category: String): Call<ArticlesResponse>
}