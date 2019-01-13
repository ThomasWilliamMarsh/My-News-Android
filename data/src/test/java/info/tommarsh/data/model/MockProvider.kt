package info.tommarsh.data.model

import info.tommarsh.core.network.NetworkException
import info.tommarsh.data.model.local.Article
import info.tommarsh.data.model.local.Category
import info.tommarsh.data.model.local.Source
import info.tommarsh.data.model.remote.*
import info.tommarsh.domain.model.*

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
    val thumbnail = Thumbnails(
        Medium(280, "http://url1.com", 500)
    )
    val contentDetails = ContentDetails("videoId1")
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
    val video = PlaylistItemResponse("id", snippet, contentDetails)
    val playlist = PlaylistResponse("token", "token", listOf(video, video))
    val videoModel = PlaylistItemModel("videoId1", "title1", "2018-12-17T14:02:30Z", "http://url1.com")
    val playlistModel = PlaylistModel("token", "token", listOf(videoModel, videoModel))
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
        "id",
        "name",
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
        "https://test.com/image.jpg"
    )
    val categoryModel = CategoryModel(
        "id",
        "name",
        true
    )

    /**
     * Errors
     */
    val noInternet = NetworkException.NoInternetException()
}