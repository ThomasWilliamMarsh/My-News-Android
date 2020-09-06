package info.tommarsh.mynews.search.model

import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.util.TimeHelper

internal fun List<ArticleModel>.toSearchViewModel(timeHelper: TimeHelper): List<SearchItemViewModel> {
    return map { domainModel ->
        val published = timeHelper.timeBetween(isoString = domainModel.publishedAt)
        SearchItemViewModel(
            author = domainModel.author,
            description = domainModel.description,
            publishedAt = published,
            title = domainModel.title,
            url = domainModel.url,
            content = domainModel.content,
            urlToImage = domainModel.urlToImage,
            category = domainModel.category
        )
    }
}