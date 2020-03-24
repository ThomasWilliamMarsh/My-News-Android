package info.tommarsh.mynews.core

import info.tommarsh.mynews.core.article.data.local.model.Article
import info.tommarsh.mynews.core.article.data.local.model.Source
import info.tommarsh.mynews.core.article.data.remote.model.ArticleResponse
import info.tommarsh.mynews.core.article.data.remote.model.ArticlesResponse
import info.tommarsh.mynews.core.article.data.remote.model.SourceResponse
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.article.domain.model.SourceModel
import info.tommarsh.mynews.core.category.data.local.model.Category
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.video.data.remote.model.*
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel
import info.tommarsh.mynews.core.video.domain.model.PlaylistModel

object MockProvider {

    /**
     * Network
     */
    val sourceResponse =
        SourceResponse("id", "name")
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
    val articlesResponse =
        ArticlesResponse(
            listOf(
                articleResponse,
                articleResponse
            ), "ok", 2
        )
    val thumbnail = Thumbnails(
        Medium(280, "http://url1.com", 500)
    )
    val contentDetails =
        ContentDetails("videoId1")
    val snippet = Snippet(
        "channel1",
        "id1",
        "channelTitle1",
        "description1",
        "playlist1",
        0,
        "2018-12-17T14:02:30Z",
        thumbnail,
        "title1"

    )
    val video = PlaylistItemResponse(
        "id",
        snippet,
        contentDetails
    )
    val playlist =
        PlaylistResponse(
            "token",
            "token",
            listOf(
                video,
                video
            )
        )
    val videoModel =
        PlaylistItemModel(
            "videoId1",
            "title1",
            "2018-12-17T14:02:30Z",
            "http://url1.com"
        )
    val playlistModel = PlaylistModel(
        "token",
        "token",
        listOf(
            videoModel,
            videoModel
        )
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
        "https://test.com/image.jpg",
        "top-news"
    )
    val category = Category(
        "top-news",
        "Top News",
        true
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
        "https://test.com/image.jpg",
        "top-news"
    )
    val categoryModel = CategoryModel(
        "top-news",
        "Top News",
        true
    )

    /**
     * Errors
     */
    val noInternet = NetworkException.NoInternetException()
}