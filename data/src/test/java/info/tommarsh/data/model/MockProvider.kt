package info.tommarsh.data.model

import info.tommarsh.core.network.NetworkException
import info.tommarsh.data.model.local.Article
import info.tommarsh.data.model.local.Source
import info.tommarsh.data.model.remote.ArticleResponse
import info.tommarsh.data.model.remote.ArticlesResponse
import info.tommarsh.data.model.remote.SourceResponse
import info.tommarsh.domain.model.ArticleModel
import info.tommarsh.domain.model.SourceModel
import kotlinx.coroutines.CompletableDeferred
import retrofit2.Response

object MockProvider {

    /**
     * Network
     */
    val sourceResponse = SourceResponse("id", "name")
    val articleResponse = ArticleResponse(
        "author",
        "content",
        "description",
        "2018-12-17T14:02:30Z",
        sourceResponse,
        "title",
        "https://www.test.com",
        "https://test.com/image.jpg"
    )
    val articlesResponse = ArticlesResponse(listOf(articleResponse, articleResponse), "ok", 2)
    val goodArticlesResponse = CompletableDeferred(
        Response.success(articlesResponse)
    )

    /**
     * DB
     */
    val source = Source("id", "name")
    val article = Article(
        "https://www.test.com",
        "author",
        "content",
        "description",
        "2018-12-17T14:02:30Z",
        source,
        "title",
        "https://test.com/image.jpg"
    )

    /**
     * Domain
     */
    val sourceModel = SourceModel("id", "name")
    val articleModel = ArticleModel(
        "author",
        "content",
        "description",
        "2018-12-17T14:02:30Z",
        sourceModel,
        "title",
        "https://www.test.com",
        "https://test.com/image.jpg"
    )

    /**
     * Errors
     */
    val noInternet = NetworkException.NoInternetException()
}