package info.tommarsh.mynews.core.article.data.local.source

import androidx.paging.PagingSource
import info.tommarsh.mynews.core.article.data.local.model.Article
import info.tommarsh.mynews.core.article.data.local.model.toDataModel
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import javax.inject.Inject

internal class ArticlesLocalDataStore
@Inject constructor(private val articlesDao: ArticlesDao) {

    fun getArticlesForCategory(category: String): PagingSource<Int, Article> {
        return articlesDao.getArticlesForCategory(category)
    }

    suspend fun clearCategory(category: String) {
        articlesDao.deleteCategory(category)
    }

    suspend fun insertArticles(items: List<ArticleModel>) {
        val model = items.map { domainModel -> domainModel.toDataModel() }
        articlesDao.insertArticles(*model.toTypedArray())
    }
}