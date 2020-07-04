package info.tommarsh.mynews.core.article.data

import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.util.ErrorLiveData
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    val errors: ErrorLiveData

    fun getBreakingNews(): Flow<List<ArticleModel>>

    fun getFeed(): Flow<List<ArticleModel>>

    suspend fun refreshBreakingNews()

    suspend fun refreshFeed(categories: List<CategoryModel>)

    suspend fun searchArticles(query: String): Outcome<List<ArticleModel>>

}