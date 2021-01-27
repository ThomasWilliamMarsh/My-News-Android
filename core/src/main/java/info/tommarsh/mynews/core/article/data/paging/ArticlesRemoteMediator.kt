package info.tommarsh.mynews.core.article.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import info.tommarsh.mynews.core.article.data.local.model.Article
import info.tommarsh.mynews.core.article.data.local.source.ArticlesLocalDataStore
import info.tommarsh.mynews.core.article.data.remote.source.ArticlesRemoteDataStore
import info.tommarsh.mynews.core.database.TransactionRunner
import info.tommarsh.mynews.core.model.Resource
import info.tommarsh.mynews.core.paging.PagingLocalDataStore

@OptIn(ExperimentalPagingApi::class)
internal class ArticlesRemoteMediator constructor(
    private val category: String,
    private val remoteArticleSource: ArticlesRemoteDataStore,
    private val localArticleSource: ArticlesLocalDataStore,
    private val pagingSource: PagingLocalDataStore,
    private val transactionRunner: TransactionRunner
) : RemoteMediator<Int, Article>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.APPEND -> pagingSource.getPageForCategory(category) + 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
        }

        val pageSize = when (loadType) {
            LoadType.REFRESH -> state.config.initialLoadSize
            else -> state.config.pageSize
        }

        return when (val resource =
            remoteArticleSource.getArticleForCategory(page, pageSize, category)) {
            is Resource.Data -> {
                val articles = resource.data.onEach { it.category = category }
                val isEndOfList = articles.size < pageSize

                transactionRunner.runTransaction {
                    if (loadType == LoadType.REFRESH) {
                        localArticleSource.clearCategory(category)
                    }
                    pagingSource.setPageForCategory(category, page)
                    localArticleSource.insertArticles(articles)
                }

                MediatorResult.Success(isEndOfList)
            }
            is Resource.Error -> {
                MediatorResult.Error(resource.error)
            }
        }
    }
}