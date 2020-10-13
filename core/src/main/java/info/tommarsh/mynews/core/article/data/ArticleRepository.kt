package info.tommarsh.mynews.core.article.data

import androidx.paging.PagingData
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.util.ErrorLiveData
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    fun getBreakingNews(): Flow<PagingData<ArticleModel>>

    fun getFeed(): Flow<List<ArticleModel>>

    fun searchArticles(query: String): Flow<PagingData<ArticleModel>>
}