package info.tommarsh.mynews.core.article.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import info.tommarsh.mynews.core.article.data.local.model.toDomainModel
import info.tommarsh.mynews.core.article.data.local.source.ArticlesLocalDataStore
import info.tommarsh.mynews.core.article.data.paging.ArticlesRemoteMediator
import info.tommarsh.mynews.core.article.data.paging.SearchPagingSource
import info.tommarsh.mynews.core.article.data.remote.source.ArticlesRemoteDataStore
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.paging.PagingLocalDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleDataRepository
@Inject internal constructor(
    private val local: ArticlesLocalDataStore,
    private val remote: ArticlesRemoteDataStore,
    private val paging: PagingLocalDataStore
) : ArticleRepository {

    override fun getArticlesForCategory(
        category: String,
        pageSize: Int
    ): Flow<PagingData<ArticleModel>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize, initialLoadSize = pageSize),
            remoteMediator = ArticlesRemoteMediator(category, remote, local, paging)
        ) { local.getArticlesForCategory(category) }.flow.map { page ->
            page.map { article -> article.toDomainModel() }
        }
    }

    override fun searchArticles(query: String, pageSize: Int): Flow<PagingData<ArticleModel>> {
        return Pager(
            config = PagingConfig(pageSize, initialLoadSize = pageSize),
            pagingSourceFactory = { SearchPagingSource(query, remote) }
        ).flow
    }
}