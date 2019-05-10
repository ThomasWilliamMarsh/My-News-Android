package info.tommarsh.data.source.local.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import info.tommarsh.data.model.local.mapper.ArticleDataToDomainMapper
import info.tommarsh.data.model.local.mapper.ArticleDomainToDataMapper
import info.tommarsh.domain.model.ArticleModel
import javax.inject.Inject

class ArticlesLocalDataStore
@Inject constructor(
    private val articlesDao: ArticlesDao,
    private val dataMapper: ArticleDataToDomainMapper,
    private val domainMapper: ArticleDomainToDataMapper
) {

    fun getBreakingNews(): LiveData<List<ArticleModel>> {
        val model = articlesDao.getBreakingArticles()
        return Transformations.map(model) { domain -> domain.map { dataMapper.map(it) } }
    }

    fun getFeed(): LiveData<List<ArticleModel>> {
        val model = articlesDao.getFeed()
        return Transformations.map(model) { domain -> domain.map { dataMapper.map(it) } }
    }

    suspend fun saveBreakingNews(items: List<ArticleModel>) {
        val model = items.map { domainMapper.map(it) }
        articlesDao.replaceBreakingArticles(model)
    }

    suspend fun saveCategory(category: String, items: List<ArticleModel>) {
        val model = items.map {
            domainMapper.map(it).also { article -> article.category = category }
        }
        articlesDao.replaceCategories(category, model)
    }
}