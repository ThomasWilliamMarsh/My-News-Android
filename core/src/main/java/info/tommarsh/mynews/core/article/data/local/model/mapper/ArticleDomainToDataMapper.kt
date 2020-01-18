package info.tommarsh.mynews.core.article.data.local.model.mapper

import info.tommarsh.mynews.core.article.data.local.model.Article
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.model.Mapper
import javax.inject.Inject

class ArticleDomainToDataMapper
@Inject constructor(
    private val sourceMapper: SourceDomainToDataMapper
) : Mapper<List<ArticleModel>, List<Article>> {

    override fun map(from: List<ArticleModel>) = from.map {
        Article(
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