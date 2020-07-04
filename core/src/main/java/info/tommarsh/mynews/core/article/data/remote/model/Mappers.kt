package info.tommarsh.mynews.core.article.data.remote.model

import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.article.domain.model.SourceModel

internal fun ArticleResponse.toDomainModel() = ArticleModel(
    author = author.orEmpty(),
    content = content.orEmpty(),
    description = description.orEmpty(),
    publishedAt = publishedAt.orEmpty(),
    source = source.toDomainModel(),
    title = title.orEmpty(),
    url = url.orEmpty(),
    urlToImage = urlToImage.orEmpty()
)

internal fun ArticlesResponse.toDomainList() =
    articles.map { articleResponse -> articleResponse.toDomainModel() }

internal fun SourceResponse.toDomainModel() = SourceModel(
    id = id.orEmpty(),
    name = name.orEmpty()
)