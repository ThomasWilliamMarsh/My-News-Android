package info.tommarsh.mynews.core.article.data.local.source

import android.util.Log
import androidx.paging.PagingSource
import info.tommarsh.mynews.core.article.data.local.model.Article
import info.tommarsh.mynews.core.article.data.local.model.toDataModel
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.di.NetworkModule.STANDARD_PAGE_SIZE
import java.lang.Exception
import javax.inject.Inject

internal class ArticlesLocalDataStore
@Inject constructor(private val articlesDao: ArticlesDao) {

    suspend fun getPageForCategory(category: String, pageSize: Int) : Int {
        val numArticles = articlesDao.getOffsetForCategory(category)
        return if(numArticles < pageSize) 1
        else numArticles / pageSize
    }

    fun getArticlesForCategory(category: String): PagingSource<Int, Article> {
        return articlesDao.getArticlesForCategory(category)
    }

    suspend fun clearCategory(category: String){
        articlesDao.deleteCategory(category)
    }

    suspend fun insertArticles(items: List<ArticleModel>) {

        try {
            val model = items.map { domainModel -> domainModel.toDataModel() }
            articlesDao.insertArticles(*model.toTypedArray())
        }catch (exception: Exception) {
            Log.e("oh no", "cwash")
        }
    }
}