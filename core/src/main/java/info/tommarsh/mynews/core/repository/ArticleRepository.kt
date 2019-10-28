package info.tommarsh.mynews.core.repository

import info.tommarsh.mynews.core.Outcome
import info.tommarsh.mynews.core.errors.ErrorLiveData
import info.tommarsh.mynews.core.model.ArticleModel
import info.tommarsh.mynews.core.model.CategoryModel
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    val errors: ErrorLiveData

    fun getBreakingNews(source: String): Flow<List<ArticleModel>>

    fun getFeed(): Flow<List<ArticleModel>>

    suspend fun refreshBreakingNews()

    suspend fun refreshFeed(categories: List<CategoryModel>)

    suspend fun searchArticles(query: String): Outcome<List<ArticleModel>>

}