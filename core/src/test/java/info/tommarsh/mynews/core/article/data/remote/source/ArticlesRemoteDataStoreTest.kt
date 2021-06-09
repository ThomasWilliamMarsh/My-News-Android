package info.tommarsh.mynews.core.article.data.remote.source

import info.tommarsh.mynews.core.article.data.remote.model.ArticlesResponse
import info.tommarsh.mynews.core.article.data.remote.model.toDomainModel
import info.tommarsh.mynews.core.di.NetworkModule.STANDARD_PAGE_SIZE
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Resource
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.ConnectionManager
import info.tommarsh.mynews.core.util.NetworkHelper
import info.tommarsh.mynews.test.UnitTest
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

class ArticlesRemoteDataStoreTest : UnitTest<ArticlesRemoteDataStore>() {

    private val category = fixture<String>()
    private val query = fixture<String>()
    private val country = fixture<String>()

    private val articlesResponse = fixture<ArticlesResponse>()
    private val goodResponse = Response.success(articlesResponse)

    private val api = mock<ArticleApiService> {
        onBlocking {
            getArticlesForCategory(
                page = 1,
                category = category,
                country = country
            )
        }.thenReturn(
            goodResponse
        )

        onBlocking { searchArticles(page = 1, query = query) }.thenReturn(goodResponse)
    }
    private val connectionManager = mock<ConnectionManager> {
        on { isConnected }.thenReturn(true)
    }
    private val preferences = mock<PreferencesRepository> {
        on { getCountry() }.thenReturn(country)
    }
    private val network = NetworkHelper(connectionManager)

    @Test
    fun `Successfully search for articles`() = runBlockingTest {
        val expected = Resource.Data(articlesResponse.articles.map { it.toDomainModel() })

        val actual = sut.searchArticles(
            page = 1,
            query = query
        )

        verify(api).searchArticles(
            query = query,
            page = 1,
            sortBy = "publishedAt"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `Fail to search for articles `() = runBlockingTest {
        whenever(connectionManager.isConnected).thenReturn(false)

        val resource = sut.searchArticles(
            page = 1,
            query = query
        )


        assertTrue(resource is Resource.Error)
        assertTrue((resource as Resource.Error).error is NetworkException.NoInternetException)
    }

    @Test
    fun `Successfully get articles from category`() = runBlockingTest {
        val expected = Resource.Data(articlesResponse.articles.map { it.toDomainModel() })

        val actual = sut.getArticleForCategory(
            page = 1,
            pageSize = STANDARD_PAGE_SIZE,
            category = category
        )

        verify(api).getArticlesForCategory(
            category = category,
            page = 1,
            pageSize = STANDARD_PAGE_SIZE,
            country = country
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `Fail to get articles from category`() = runBlockingTest {
        whenever(connectionManager.isConnected).thenReturn(false)

        val resource = sut.getArticleForCategory(
            page = 1,
            pageSize = STANDARD_PAGE_SIZE,
            category = category
        )

        assertTrue(resource is Resource.Error)
        assertTrue((resource as Resource.Error).error is NetworkException.NoInternetException)
    }

    override fun createSut(): ArticlesRemoteDataStore {
        return ArticlesRemoteDataStore(
            network,
            api,
            preferences
        )
    }
}