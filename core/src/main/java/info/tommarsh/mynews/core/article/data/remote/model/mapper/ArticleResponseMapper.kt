package info.tommarsh.mynews.core.article.data.remote.model.mapper

import info.tommarsh.mynews.core.article.data.remote.model.ArticlesResponse
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.model.Mapper
import javax.inject.Inject

class ArticleResponseMapper
@Inject
constructor(private val sourceMapper: SourceResponseMapper) :
    Mapper<ArticlesResponse, List<ArticleModel>> {

    override fun map(from: ArticlesResponse) =
        from.articles.map {
            ArticleModel(
                author = it.author.orEmpty(),
                content = it.content.orEmpty(),
                description = it.description.orEmpty(),
                publishedAt = it.publishedAt.orEmpty(),
                source = sourceMapper.map(it.source),
                title = it.title.orEmpty(),
                url = it.url.orEmpty(),
                urlToImage = it.urlToImage.orEmpty()
            )
        }
}