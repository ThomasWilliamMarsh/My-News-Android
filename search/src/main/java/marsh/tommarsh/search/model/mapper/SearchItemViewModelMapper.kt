package marsh.tommarsh.search.model.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.core.TimeHelper
import info.tommarsh.core.model.ArticleModel
import marsh.tommarsh.search.model.SearchItemViewModel
import javax.inject.Inject

class SearchItemViewModelMapper
@Inject constructor(private val timeHelper: TimeHelper) : Mapper<List<ArticleModel>, List<SearchItemViewModel>> {
    override fun map(from: List<ArticleModel>): List<SearchItemViewModel> = from.map {
        val published = timeHelper.timeBetween(isoString = it.publishedAt)
        SearchItemViewModel(
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