package info.tommarsh.presentation.model.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.core.TimeHelper
import info.tommarsh.domain.model.ArticleModel
import info.tommarsh.presentation.model.ArticleViewModel
import javax.inject.Inject

class ArticleViewModelMapper
@Inject constructor(private val timeHelper: TimeHelper) : Mapper<ArticleModel, ArticleViewModel> {
    override fun map(from: ArticleModel): ArticleViewModel {
        val published = timeHelper.timeBetween(isoString = from.publishedAt)
        return ArticleViewModel(
            author = from.author,
            description = from.description,
            publishedAt = published,
            title = from.title,
            url = from.url,
            urlToImage = from.urlToImage,
            category = from.category
        )
    }
}