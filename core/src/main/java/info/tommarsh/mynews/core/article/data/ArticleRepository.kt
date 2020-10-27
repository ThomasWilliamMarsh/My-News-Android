package info.tommarsh.mynews.core.article.data

import androidx.paging.PagingData
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.di.NetworkModule.STANDARD_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    fun getArticlesForCategory(
        category: String,
        pageSize: Int = STANDARD_PAGE_SIZE
    ): Flow<PagingData<ArticleModel>>

    fun searchArticles(
        query: String,
        pageSize: Int = STANDARD_PAGE_SIZE
    ): Flow<PagingData<ArticleModel>>
}