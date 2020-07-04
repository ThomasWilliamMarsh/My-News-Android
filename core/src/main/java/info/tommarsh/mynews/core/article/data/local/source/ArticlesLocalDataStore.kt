package info.tommarsh.mynews.core.article.data.local.source

import info.tommarsh.mynews.core.article.data.local.model.toDataModel
import info.tommarsh.mynews.core.article.data.local.model.toDomainModel
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ArticlesLocalDataStore
@Inject constructor(private val articlesDao: ArticlesDao) {

    fun getBreakingNews(): Flow<List<ArticleModel>> {
        return articlesDao.getBreakingArticles().map { databaseModels ->
            databaseModels.map { model -> model.toDomainModel() }
        }
    }

    fun getFeed(): Flow<List<ArticleModel>> {
        return articlesDao.getFeed().map { databaseModels ->
            databaseModels.map { model -> model.toDomainModel() }
        }
    }

    suspend fun deleteUnselectedCategories() {
        return articlesDao.deleteUnselectedCategories()
    }

    suspend fun saveArticles(items: List<ArticleModel>) {
        val model = items.map { domainModel -> domainModel.toDataModel() }
        articlesDao.replaceBreakingArticles(model)
    }
}