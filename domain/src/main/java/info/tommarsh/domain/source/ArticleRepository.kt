package info.tommarsh.domain.source

import androidx.lifecycle.LiveData
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.core.network.Outcome
import info.tommarsh.domain.model.ArticleModel

interface ArticleRepository {

    val errors: ErrorLiveData

    fun getBreakingNews(source: String): LiveData<List<ArticleModel>>

    fun getArticlesForCategory(category: String): List<ArticleModel>

    fun refreshBreakingNews()

    fun searchArticles(query: String): Outcome<List<ArticleModel>>

}