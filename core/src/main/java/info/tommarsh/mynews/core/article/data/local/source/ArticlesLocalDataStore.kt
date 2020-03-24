package info.tommarsh.mynews.core.article.data.local.source

import info.tommarsh.mynews.core.article.data.local.model.mapper.ArticleDataToDomainMapper
import info.tommarsh.mynews.core.article.data.local.model.mapper.ArticleDomainToDataMapper
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ArticlesLocalDataStore
@Inject constructor(
    private val articlesDao: ArticlesDao,
    private val dataMapper: ArticleDataToDomainMapper,
    private val domainMapper: ArticleDomainToDataMapper
) {

    fun getBreakingNews(): Flow<List<ArticleModel>> {
        return articlesDao.getBreakingArticles().map { dataMapper.map(it) }
    }

    fun getFeed(): Flow<List<ArticleModel>> {
        return articlesDao.getFeed().map { dataMapper.map(it) }
    }

    suspend fun deleteUnselectedCategories() {
        return articlesDao.deleteUnselectedCategories()
    }

    suspend fun saveArticles(items: List<ArticleModel>) {
        val model = domainMapper.map(items)
        articlesDao.replaceBreakingArticles(model)
    }
}