package info.tommarsh.mynews.core.article.data.local.model

import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.article.domain.model.SourceModel

internal fun Article.toDomainModel() = ArticleModel(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    source = source.toDomainModel(),
    title = title,
    url = url,
    urlToImage = urlToImage,
    category = category
)

internal fun ArticleModel.toDataModel() = Article(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    source = source.toDataModel(),
    title = title,
    url = url,
    urlToImage = urlToImage,
    category = category
)

internal fun Source.toDomainModel() = SourceModel(
    id = id,
    name = name
)

internal fun SourceModel.toDataModel() = Source(
    id = id,
    name = name
)