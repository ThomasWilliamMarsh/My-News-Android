package info.tommarsh.mynews.core.article.data.remote.source

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.core.MockProvider.articleModel
import info.tommarsh.mynews.core.MockProvider.articlesResponse
import info.tommarsh.mynews.core.di.NetworkModule.STANDARD_PAGE_SIZE
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Resource
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.ConnectionManager
import info.tommarsh.mynews.core.util.NetworkHelper
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import retrofit2.Response

class ArticlesRemoteDataStoreTest {

    private val goodResponse = Response.success(articlesResponse)

    private val api = mock<ArticleApiService> {
        onBlocking {
            getArticlesForCategory(
                page = 1,
                category = "category",
                country = "gb"
            )
        }.thenReturn(
            goodResponse
        )

        onBlocking { searchArticles(page = 1, query = "query") }.thenReturn(goodResponse)
    }
    private val connectionManager = mock<ConnectionManager> {
        on { isConnected }.thenReturn(true)
    }
    private val preferences = mock<PreferencesRepository> {
        on { getCountry() }.thenReturn("gb")
    }
    private val network = NetworkHelper(connectionManager)
    private val remoteDataStore =
        ArticlesRemoteDataStore(
            network,
            api,
            preferences
        )


    @Test
    fun `Successfully search for articles`() = runBlockingTest {
        val expected = Resource.Data(listOf(articleModel, articleModel))

        val actual = remoteDataStore.searchArticles(
            page = 1,
            query = "query"
        )

        verify(api).searchArticles(
            query = "query",
            page = 1,
            sortBy = "publishedAt"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `Fail to search for articles `() = runBlockingTest {
        whenever(connectionManager.isConnected).thenReturn(false)

        val resource = remoteDataStore.searchArticles(
            page = 1,
            query = "query"
        )


        assertTrue(resource is Resource.Error)
        assertTrue((resource as Resource.Error).error is NetworkException.NoInternetException)
    }

    @Test
    fun `Successfully get articles from category`() = runBlockingTest {
        val expected = Resource.Data(listOf(articleModel, articleModel))

        val actual = remoteDataStore.getArticleForCategory(
            page = 1,
            pageSize = STANDARD_PAGE_SIZE,
            category = "category"
        )

        verify(api).getArticlesForCategory(
            category = "category",
            page = 1,
            pageSize = STANDARD_PAGE_SIZE,
            country = "gb"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `Fail to get articles from category`() = runBlockingTest {
        whenever(connectionManager.isConnected).thenReturn(false)

        val resource = remoteDataStore.getArticleForCategory(
            page = 1,
            pageSize = STANDARD_PAGE_SIZE,
            category = "category"
        )

        assertTrue(resource is Resource.Error)
        assertTrue((resource as Resource.Error).error is NetworkException.NoInternetException)
    }
}