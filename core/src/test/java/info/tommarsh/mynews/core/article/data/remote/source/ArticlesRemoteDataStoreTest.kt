package info.tommarsh.mynews.core.article.data.remote.source

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.core.MockProvider.articleModel
import info.tommarsh.mynews.core.MockProvider.articlesResponse
import info.tommarsh.mynews.core.MockProvider.noInternet
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.di.NetworkModule.STANDARD_PAGE_SIZE
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.util.ConnectionManager
import info.tommarsh.mynews.core.util.NetworkHelper
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import retrofit2.Response

class ArticlesRemoteDataStoreTest {

    private val goodResponse = Response.success(articlesResponse)

    private val api = mock<ArticleApiService> {
        onBlocking { getArticlesForCategory(page = 1, category = "category") }.thenReturn(
            goodResponse
        )

        onBlocking { searchArticles(page = 1, query = "query") }.thenReturn(goodResponse)
    }
    private val connectionManager = mock<ConnectionManager> {
        on { isConnected }.thenReturn(true)
    }
    private val network = NetworkHelper(connectionManager)
    private val remoteDataStore =
        ArticlesRemoteDataStore(
            network,
            api
        )

    @Test
    fun `Fail to search for articles `() = runBlockingTest {
        whenever(connectionManager.isConnected).thenReturn(false)

        val outcome = remoteDataStore.searchArticles(
            page = 1,
            query = "query"
        )


        assertTrue(outcome is Outcome.Error)
        assertTrue((outcome as Outcome.Error).error is NetworkException.NoInternetException)
    }

    @Test
    fun `Successfully search articles from network`() = runBlockingTest {
        val expected = Outcome.Success(listOf(articleModel, articleModel))

        val actual = remoteDataStore.getArticleForCategory(
            page = 1,
            pageSize = STANDARD_PAGE_SIZE,
            category = "category"
        )

        assertEquals(expected, actual)
    }

    @Test
    fun `Fail to get category articles from network`() = runBlockingTest {
        whenever(connectionManager.isConnected).thenReturn(false)

        val outcome = remoteDataStore.getArticleForCategory(
            page = 1,
            pageSize = STANDARD_PAGE_SIZE,
            category = "category"
        )

        assertTrue(outcome is Outcome.Error)
        assertTrue((outcome as Outcome.Error).error is NetworkException.NoInternetException)
    }

    @Test
    fun `Get articles by category`() = runBlockingTest {
        val expected = Outcome.Success(listOf(articleModel, articleModel))

        val actual = remoteDataStore.searchArticles(
            page = 1,
            query = "query"
        )

        assertEquals(expected, actual)
    }
}