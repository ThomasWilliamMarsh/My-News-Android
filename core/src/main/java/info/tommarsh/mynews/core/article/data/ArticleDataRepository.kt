package info.tommarsh.mynews.core.article.data

import androidx.paging.*
import info.tommarsh.mynews.core.article.data.local.model.toDomainModel
import info.tommarsh.mynews.core.article.data.local.source.ArticlesLocalDataStore
import info.tommarsh.mynews.core.article.data.paging.ArticlesRemoteMediator
import info.tommarsh.mynews.core.article.data.paging.SearchPagingSource
import info.tommarsh.mynews.core.article.data.remote.source.ArticlesRemoteDataStore
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.database.TransactionRunner
import info.tommarsh.mynews.core.paging.PagingLocalDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ArticleDataRepository
@Inject internal constructor(
    private val local: ArticlesLocalDataStore,
    private val remote: ArticlesRemoteDataStore,
    private val paging: PagingLocalDataStore,
    private val transactionRunner: TransactionRunner
) : ArticleRepository {

    override fun getArticlesForCategory(
        category: String,
        pageSize: Int
    ): Flow<PagingData<ArticleModel>> {
        val pagingFactory = { local.getArticlesForCategory(category) }
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize,
                enablePlaceholders = false
            ),
            remoteMediator = ArticlesRemoteMediator(category, remote, local, paging, transactionRunner),
            pagingSourceFactory = pagingFactory
        ).flow.map { page ->
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