package info.tommarsh.data.model.remote.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.data.model.remote.ArticlesResponse
import info.tommarsh.core.model.ArticleModel
import javax.inject.Inject

class ArticleResponseMapper
@Inject
constructor(private val sourceMapper: SourceResponseMapper) : Mapper<ArticlesResponse, List<ArticleModel>> {

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