package info.tommarsh.mynews.search.model

import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.article.domain.model.SourceModel
import info.tommarsh.mynews.core.model.NetworkException

object MockModelProvider {

    val fakeIsoTime = "2019-01-01T11:00:00+0000"

    //region article
    val sourceModel = SourceModel("id", "name")
    val articleModel = ArticleModel(
        "author", "content", "description",
        fakeIsoTime,
        sourceModel, "title",
        "url", "imageUrl", "business"
    )
    val articleViewModel =
        SearchItemViewModel(
            "author",
            "description",
            "1 hour ago",
            "title",
            "content",
            "url",
            "imageUrl",
            "business"
        )

    /**
     * Errors
     */
    val noInternet = NetworkException.NoInternetException()
}