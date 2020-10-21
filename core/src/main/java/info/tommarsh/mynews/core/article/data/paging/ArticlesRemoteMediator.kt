package info.tommarsh.mynews.core.article.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import info.tommarsh.mynews.core.article.data.local.model.Article
import info.tommarsh.mynews.core.article.data.local.source.ArticlesLocalDataStore
import info.tommarsh.mynews.core.article.data.remote.source.ArticlesRemoteDataStore
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.preferences.PreferencesRepository

@OptIn(ExperimentalPagingApi::class)
internal class ArticlesRemoteMediator constructor(
    private val category: String,
    private val remoteArticleSource: ArticlesRemoteDataStore,
    private val localArticleSource: ArticlesLocalDataStore) : RemoteMediator<Int, Article>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.APPEND -> localArticleSource.getPageForCategory(category, state.config.pageSize)
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
        }

        val pageSize = when (loadType) {
            LoadType.REFRESH -> state.config.initialLoadSize
            else -> state.config.pageSize
        }

        if (loadType == LoadType.REFRESH) {
            localArticleSource.clearCategory(category)
        }

        return when (val outcome = remoteArticleSource.getArticleForCategory(page, pageSize, category)) {
            is Outcome.Success -> {
                val articles = outcome.data.also { it.forEach { it.category = category } }
                val isEndOfList = articles.isEmpty()

                localArticleSource.insertArticles(articles)

                MediatorResult.Success(endOfPaginationReached = isEndOfList)
            }
            is Outcome.Error -> {
                MediatorResult.Error(outcome.error)           }
        }
    }
}