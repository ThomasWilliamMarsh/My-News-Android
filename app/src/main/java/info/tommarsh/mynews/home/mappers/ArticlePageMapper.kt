package info.tommarsh.mynews.home.mappers

import androidx.paging.PagingData
import androidx.paging.map
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.home.model.ArticleViewModel
import javax.inject.Inject

class ArticlePageMapper @Inject constructor(
    private val timeHelper: TimeHelper
) {

    fun map(page: PagingData<ArticleModel>): PagingData<ArticleViewModel> {
        return page.map { model -> model.toViewModel(timeHelper) }
    }
}

internal fun ArticleModel.toViewModel(timeHelper: TimeHelper): ArticleViewModel {
    val published = timeHelper.timeBetween(isoString = publishedAt)
    return ArticleViewModel(
        author = author,
        description = description,
        publishedAt = published,
        title = title,
        url = url,
        content = content,
        urlToImage = urlToImage,
        category = category
    )
}