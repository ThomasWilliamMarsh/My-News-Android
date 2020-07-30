package info.tommarsh.mynews.presentation.model

import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel

internal fun PlaylistItemModel.toViewModel(timeHelper: TimeHelper) = PlaylistItemViewModel(
    videoId = videoId,
    title = title,
    publishedAt = timeHelper.timeBetween(isoString = publishedAt),
    thumbnail = thumbnail
)

internal fun List<ArticleModel>.toCarousels(
    categories: List<CategoryModel>,
    timeHelper: TimeHelper
): List<CarouselViewModel> {
    return categories.map { category ->
        val name = category.name
        val articles = filter { it.category == category.id }.map { it.toViewModel(timeHelper) }
        CarouselViewModel(name, articles)
    }
}

internal fun ArticleModel.toViewModel(timeHelper: TimeHelper): ArticleViewModel {
    val published = timeHelper.timeBetween(isoString = publishedAt)
    return ArticleViewModel(
        author = author,
        description = description,
        publishedAt = published,
        title = title,
        url = url,
        content = content,
        urlToImage = urlToImage,
        category = category
    )
}