package info.tommarsh.mynews.repository.source.local.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import info.tommarsh.mynews.core.model.ArticleModel
import info.tommarsh.mynews.repository.model.local.mapper.ArticleDataToDomainMapper
import info.tommarsh.mynews.repository.model.local.mapper.ArticleDomainToDataMapper
import javax.inject.Inject

internal class ArticlesLocalDataStore
@Inject constructor(
    private val articlesDao: ArticlesDao,
    private val dataMapper: ArticleDataToDomainMapper,
    private val domainMapper: ArticleDomainToDataMapper
) {

    fun getBreakingNews(): LiveData<List<ArticleModel>> {
        return articlesDao.getBreakingArticles().map(dataMapper::map)
    }

    fun getFeed(): LiveData<List<ArticleModel>> {
        return articlesDao.getFeed().map(dataMapper::map)
    }

    suspend fun deleteUnselectedCategories() {
        return articlesDao.deleteUnselectedCategories()
    }

    suspend fun saveBreakingNews(items: List<ArticleModel>) {
        val model = domainMapper.map(items)
        articlesDao.replaceBreakingArticles(model)
    }

    suspend fun saveCategory(category: String, items: List<ArticleModel>) {
        val model = domainMapper.map(items).also { articles -> articles.forEach { it.category = category } }
        articlesDao.replaceCategories(category, model)
    }
}