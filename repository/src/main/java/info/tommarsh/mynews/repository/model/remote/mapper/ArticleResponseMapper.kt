package info.tommarsh.mynews.repository.model.remote.mapper

import info.tommarsh.mynews.core.Mapper
import info.tommarsh.mynews.core.model.ArticleModel
import info.tommarsh.mynews.repository.model.remote.ArticlesResponse
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