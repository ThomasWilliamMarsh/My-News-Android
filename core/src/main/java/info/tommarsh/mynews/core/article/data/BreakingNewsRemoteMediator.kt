package info.tommarsh.mynews.core.article.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import info.tommarsh.mynews.core.article.data.local.model.Article
import info.tommarsh.mynews.core.article.data.local.source.ArticlesLocalDataStore
import info.tommarsh.mynews.core.article.data.remote.source.ArticlesRemoteDataStore
import info.tommarsh.mynews.core.di.NetworkModule.NETWORK_PAGE_SIZE
import info.tommarsh.mynews.core.model.Outcome
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
internal class BreakingNewsRemoteMediator
@Inject constructor(
    private val remoteArticleSource: ArticlesRemoteDataStore,
    private val localArticleSource: ArticlesLocalDataStore
) : RemoteMediator<Int, Article>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {

        var page = localArticleSource.getBreakingNewsPage() + 1
        if (loadType == LoadType.REFRESH) {
            localArticleSource.clearBreakingNewsArticles().also { page = 1}
        }

        return when (val outcome = remoteArticleSource.getBreakingNews(page = page)) {
            is Outcome.Success -> {
                val articles = outcome.data
                val isEndOfList = articles.size < NETWORK_PAGE_SIZE

                localArticleSource.insertArticles(articles)

                MediatorResult.Success(endOfPaginationReached = isEndOfList)
            }
            is Outcome.Error -> {
                MediatorResult.Error(outcome.error)
            }
        }
    }
}