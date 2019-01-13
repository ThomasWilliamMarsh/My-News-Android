package info.tommarsh.domain.source

import androidx.lifecycle.LiveData
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.core.network.Outcome
import info.tommarsh.domain.model.ArticleModel
import info.tommarsh.domain.model.CategoryModel

interface ArticleRepository {

    val errors: ErrorLiveData

    fun getBreakingNews(source: String): LiveData<List<ArticleModel>>

    fun refreshBreakingNews()

    fun getFeed(): LiveData<List<ArticleModel>>

    suspend fun refreshFeed(categories: List<CategoryModel>)

    fun searchArticles(query: String): Outcome<List<ArticleModel>>

}