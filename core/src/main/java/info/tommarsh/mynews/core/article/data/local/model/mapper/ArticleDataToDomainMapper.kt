package info.tommarsh.mynews.core.article.data.local.model.mapper

import info.tommarsh.mynews.core.article.data.local.model.Article
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.model.Mapper
import javax.inject.Inject

class ArticleDataToDomainMapper
@Inject constructor(
    private val sourceMapper: SourceDataToDomainMapper
) : Mapper<List<Article>, List<ArticleModel>> {

    override fun map(from: List<Article>) = from.map {
        ArticleModel(
            author = it.author,
            content = it.content,
            description = it.description,
            publishedAt = it.publishedAt,
            source = sourceMapper.map(it.source),
            title = it.title,
            url = it.url,
            urlToImage = it.urlToImage,
            category = it.category
        )
    }
}