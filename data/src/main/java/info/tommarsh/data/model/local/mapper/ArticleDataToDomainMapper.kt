package info.tommarsh.data.model.local.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.data.model.local.Article
import info.tommarsh.domain.model.ArticleModel
import javax.inject.Inject

class ArticleDataToDomainMapper
@Inject constructor(
    private val sourceMapper: SourceDataToDomainMapper
) : Mapper<Article, ArticleModel> {

    override fun map(from: Article) = ArticleModel(
        author = from.author,
        content = from.content,
        description = from.description,
        publishedAt = from.publishedAt,
        source = sourceMapper.map(from.source),
        title = from.title,
        url = from.url,
        urlToImage = from.urlToImage,
        category = from.category
    )
}