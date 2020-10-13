package info.tommarsh.mynews.core.article.data.local.source

import androidx.paging.PagingSource
import info.tommarsh.mynews.core.article.data.local.model.Article
import info.tommarsh.mynews.core.article.data.local.model.toDataModel
import info.tommarsh.mynews.core.article.data.local.model.toDomainModel
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.di.NetworkModule.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.roundToInt

internal class ArticlesLocalDataStore
@Inject constructor(private val articlesDao: ArticlesDao) {


    suspend fun getBreakingNewsPage() : Int {
        val numArticles = articlesDao.getBreakingArticlesOffset()
        return if(numArticles < NETWORK_PAGE_SIZE)  0
        else numArticles / NETWORK_PAGE_SIZE
    }

    fun getBreakingNews(): PagingSource<Int, Article> {
        return articlesDao.getBreakingArticles()
    }

    suspend fun insertArticles(items: List<ArticleModel>) {
        val model = items.map { domainModel -> domainModel.toDataModel() }
        articlesDao.insertArticles(*model.toTypedArray())
    }


    suspend fun clearBreakingNewsArticles() {
        articlesDao.deleteBreakingArticles()
    }

    fun getFeed(): Flow<List<ArticleModel>> {
        return articlesDao.getFeed().map { databaseModels ->
            databaseModels.map { model -> model.toDomainModel() }
        }
    }
}