package info.tommarsh.mynews.repository.model.local.mapper

import info.tommarsh.mynews.core.Mapper
import info.tommarsh.mynews.core.model.ArticleModel
import info.tommarsh.mynews.repository.model.local.Article
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