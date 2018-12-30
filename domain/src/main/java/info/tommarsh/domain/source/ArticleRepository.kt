package info.tommarsh.domain.source

import androidx.lifecycle.LiveData
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.core.network.Outcome
import info.tommarsh.domain.model.ArticleModel

interface ArticleRepository {

    val errors: ErrorLiveData

    fun getBreakingNewsObservable(source: String): LiveData<List<ArticleModel>>

    fun searchArticles(query: String): Outcome<List<ArticleModel>>

    fun refreshBreakingNews()

    fun getCategory(category: String): LiveData<List<ArticleModel>>
}