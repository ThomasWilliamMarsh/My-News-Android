package info.tommarsh.mynews.home.model

import info.tommarsh.mynews.categories.model.CategoryViewModel
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.article.domain.model.SourceModel
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel
import info.tommarsh.mynews.core.video.domain.model.PlaylistModel

object MockModelProvider {

    val fakeIsoTime = "2019-01-01T11:00:00+0000"

    //region category
    val footballCategoryModel = CategoryModel("id-football", "Football", false)
    val footballCategoryViewModel = CategoryViewModel("id-football", "Football", false)
    val entertainmentCategoryModel = CategoryModel("id-entertainment", "Entertainment", false)
    //endregion

    //region playlist
    val playlistItemModel = PlaylistItemModel(
        "id", "title",
        fakeIsoTime, "url"
    )
    val playlistModel =
        PlaylistModel(
            "next", "previous", listOf(
                playlistItemModel,
                playlistItemModel
            )
        )
    val playlistItemViewModel = PlaylistItemViewModel("id", "title", "1 hour ago", "url")
    //endregion


    //region source
    val sourceModel = SourceModel("id", "name")
    //end region

    //region article
    val articleModel = ArticleModel(
        "author", "content", "description",
        fakeIsoTime,
        sourceModel, "title",
        "url", "imageUrl", "business"
    )
    val footballArticleModel = ArticleModel(
        "author", "content", "description",
        fakeIsoTime,
        sourceModel, "Football Title",
        "url", "imageUrl", "id-football"
    )

    val entertainmentArticleModel = ArticleModel(
        "author", "content", "description",
        fakeIsoTime,
        sourceModel, "Entertainment Title",
        "url", "imageUrl", "id-entertainment"
    )


    val articleViewModel =
        ArticleViewModel(
            "author",
            "description",
            "1 hour ago",
            "title",
            "content",
            "url",
            "imageUrl",
            "business"
        )
    val footballArticleViewModel =
        ArticleViewModel(
            "author",
            "description",
            "1 hour ago",
            "Football Title",
            "content",
            "url",
            "imageUrl",
            "id-football"
        )
    val entertainmentArticleViewModel =
        ArticleViewModel(
            "author",
            "description",
            "1 hour ago",
            "Entertainment Title",
            "content",
            "url",
            "imageUrl",
            "id-entertainment"
        )
    //endregion

    /**
     * Errors
     */
    val noInternet = NetworkException.NoInternetException()
}