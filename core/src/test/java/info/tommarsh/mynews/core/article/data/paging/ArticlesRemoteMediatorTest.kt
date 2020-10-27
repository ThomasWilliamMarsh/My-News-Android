package info.tommarsh.mynews.core.article.data.paging

import androidx.paging.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.core.MockProvider.articleModel
import info.tommarsh.mynews.core.MockProvider.noInternet
import info.tommarsh.mynews.core.article.data.local.model.Article
import info.tommarsh.mynews.core.article.data.local.source.ArticlesLocalDataStore
import info.tommarsh.mynews.core.article.data.remote.source.ArticlesRemoteDataStore
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.paging.PagingLocalDataStore
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@OptIn(ExperimentalPagingApi::class)
class ArticlesRemoteMediatorTest {

    private val category = "business"
    private val remoteSource = mock<ArticlesRemoteDataStore>()
    private val localArticleSource = mock<ArticlesLocalDataStore>()
    private val pageSource = mock<PagingLocalDataStore>()

    private val config = PagingConfig(pageSize = 20)
    private val pagingState = PagingState<Int, Article>(
        config = config,
        pages = mock(),
        anchorPosition = 1,
        leadingPlaceholderCount = 1
    )

    private val remoteMediator =
        ArticlesRemoteMediator(category, remoteSource, localArticleSource, pageSource)

    @Test
    fun `Notify end of pagination`() = runBlockingTest {
        whenever(remoteSource.getArticleForCategory(1, config.initialLoadSize, category))
            .thenReturn(Outcome.Success(emptyList()))

        val actual = remoteMediator.load(
            loadType = LoadType.REFRESH,
            state = pagingState
        )

        assertTrue(actual is RemoteMediator.MediatorResult.Success)
        assertTrue((actual as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `Successful refresh`() = runBlockingTest {
        whenever(remoteSource.getArticleForCategory(1, config.initialLoadSize, category))
            .thenReturn(Outcome.Success(listOf(articleModel)))

        remoteMediator.load(
            loadType = LoadType.REFRESH,
            state = pagingState
        )

        verify(remoteSource).getArticleForCategory(1, config.initialLoadSize, category)
        verify(localArticleSource).insertArticles(listOf(articleModel))
        verify(localArticleSource).clearCategory(category)
    }

    @Test
    fun `Failed refresh`() = runBlockingTest {
        whenever(remoteSource.getArticleForCategory(1, config.initialLoadSize, category))
            .thenReturn(Outcome.Error(noInternet))

        val result = remoteMediator.load(
            loadType = LoadType.REFRESH,
            state = pagingState
        )

        assertTrue(result is RemoteMediator.MediatorResult.Error)
        verify(remoteSource).getArticleForCategory(1, config.initialLoadSize, category)
        verify(localArticleSource, never()).clearCategory(category)
        verify(localArticleSource, never()).insertArticles(listOf(articleModel))
    }

    @Test
    fun `Prepend always returns success`() = runBlockingTest {

        val result = remoteMediator.load(
            loadType = LoadType.PREPEND,
            state = pagingState
        )

        assertTrue(result is RemoteMediator.MediatorResult.Success)
    }

    @Test
    fun `Failed append`() = runBlockingTest {
        whenever(remoteSource.getArticleForCategory(3, config.pageSize, category))
            .thenReturn(Outcome.Error(noInternet))
        whenever(pageSource.getPageForCategory(category))
            .thenReturn(2)

        val result = remoteMediator.load(
            loadType = LoadType.APPEND,
            state = pagingState
        )

        assertTrue(result is RemoteMediator.MediatorResult.Error)
        verify(pageSource).getPageForCategory(category)
        verify(localArticleSource, never()).clearCategory(category)
        verify(remoteSource).getArticleForCategory(3, config.pageSize, category)
        verify(localArticleSource, never()).insertArticles(listOf(articleModel))
    }

    @Test
    fun `Successful append`() = runBlockingTest {
        whenever(remoteSource.getArticleForCategory(3, config.pageSize, category))
            .thenReturn(Outcome.Success(listOf(articleModel)))
        whenever(pageSource.getPageForCategory(category))
            .thenReturn(2)

        val result = remoteMediator.load(
            loadType = LoadType.APPEND,
            state = pagingState
        )

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        verify(pageSource).getPageForCategory(category)
        verify(localArticleSource, never()).clearCategory(category)
        verify(remoteSource).getArticleForCategory(3, config.pageSize, category)
        verify(localArticleSource).insertArticles(listOf(articleModel))
    }
}