package info.tommarsh.mynews.search.mappers

import androidx.paging.PagingData
import androidx.paging.map
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.search.model.SearchItemViewModel
import info.tommarsh.mynews.search.model.toSearchViewModel
import javax.inject.Inject

class SearchItemPageMapper @Inject constructor(
    private val timeHelper: TimeHelper
) {

    fun map(page: PagingData<ArticleModel>): PagingData<SearchItemViewModel> {
        return page.map { model -> model.toSearchViewModel(timeHelper) }
    }
}