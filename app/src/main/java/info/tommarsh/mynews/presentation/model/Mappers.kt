package info.tommarsh.mynews.presentation.model

import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.video.domain.model.PlaylistItemModel

internal fun PlaylistItemModel.toViewModel(timeHelper: TimeHelper) = PlaylistItemViewModel(
    videoId = videoId,
    title = title,
    publishedAt = timeHelper.timeBetween(isoString = publishedAt),
    thumbnail = thumbnail
)

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