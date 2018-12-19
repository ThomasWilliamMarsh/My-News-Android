package info.tommarsh.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import info.tommarsh.data.model.local.mapper.ArticleDataToDomainMapper
import info.tommarsh.data.model.local.mapper.ArticleDomainToDataMapper
import info.tommarsh.domain.model.ArticleModel
import javax.inject.Inject

class LocalDataStore
@Inject constructor(
    private val dao: ArticlesDao,
    private val dataMapper: ArticleDataToDomainMapper,
    private val domainMapper: ArticleDomainToDataMapper
) {

    fun getBreakingNews(): LiveData<List<ArticleModel>> {
        val model = dao.getBreakingArticles()
        return Transformations.map(model) { domain -> domain.map { dataMapper.map(it) } }
    }

    fun saveBreakingNews(items: List<ArticleModel>) {
        val model = items.map { domainMapper.map(it) }
        dao.insertBreakingArticles(*model.toTypedArray())
    }
}