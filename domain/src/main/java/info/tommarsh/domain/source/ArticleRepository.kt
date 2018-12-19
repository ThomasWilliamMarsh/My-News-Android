package info.tommarsh.domain.source

import androidx.lifecycle.LiveData
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.domain.model.ArticleModel

interface ArticleRepository {

    val errors: ErrorLiveData

    fun getBreakingNews(source: String): LiveData<List<ArticleModel>>

    fun refreshBreakingNews()

    fun getCategory(category: String): LiveData<List<ArticleModel>>
}