package info.tommarsh.presentation.model.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.core.TimeHelper
import info.tommarsh.core.model.ArticleModel
import info.tommarsh.presentation.model.ArticleViewModel
import javax.inject.Inject

class ArticleViewModelMapper
@Inject constructor(private val timeHelper: TimeHelper) : Mapper<List<ArticleModel>, List<ArticleViewModel>> {
    override fun map(from: List<ArticleModel>): List<ArticleViewModel> = from.map {
        val published = timeHelper.timeBetween(isoString = it.publishedAt)
        ArticleViewModel(
            author = it.author,
            description = it.description,
            publishedAt = published,
            title = it.title,
            url = it.url,
            content = it.content,
            urlToImage = it.urlToImage,
            category = it.category
        )
    }
}