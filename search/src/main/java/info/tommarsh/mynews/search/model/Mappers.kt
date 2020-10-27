package info.tommarsh.mynews.search.model

import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.util.TimeHelper

internal fun ArticleModel.toSearchViewModel(timeHelper: TimeHelper): SearchItemViewModel {
    val published = timeHelper.timeBetween(isoString = publishedAt)
    return SearchItemViewModel(
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