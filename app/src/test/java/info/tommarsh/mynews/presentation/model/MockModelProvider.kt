package info.tommarsh.mynews.presentation.model

import info.tommarsh.core.NetworkException
import info.tommarsh.core.model.*
import info.tommarsh.categories.model.CategoryViewModel

object MockModelProvider {

    val fakeIsoTime = "2019-01-01T11:00:00+0000"

    //region category
    val categoryModel = CategoryModel("id", "name", false)
    val categoryViewModel = CategoryViewModel("id", "name", false)
    //endregion

    //region playlist
    val playlistItemModel = PlaylistItemModel("id", "title",
        fakeIsoTime, "url")
    val playlistModel =
        PlaylistModel("next", "previous", listOf(
            playlistItemModel,
            playlistItemModel
        ))
    val playlistItemViewModel = PlaylistItemViewModel("id", "title", "1 hour ago", "url")
    //endregion

    //region article
    val sourceModel = SourceModel("id", "name")
    val articleModel = ArticleModel(
        "author", "content", "description",
        fakeIsoTime,
        sourceModel, "title",
        "url", "imageUrl", "top-news"
    )
    val articleViewModel =
        ArticleViewModel("author", "description", "1 hour ago", "title", "content", "url", "imageUrl", "top-news")

    /**
     * Errors
     */
    val noInternet = NetworkException.NoInternetException()
}