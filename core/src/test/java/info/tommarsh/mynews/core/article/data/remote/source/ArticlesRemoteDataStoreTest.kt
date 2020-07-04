package info.tommarsh.mynews.core.article.data.remote.source

import com.nhaarman.mockitokotlin2.mock
import info.tommarsh.mynews.core.MockProvider.articleModel
import info.tommarsh.mynews.core.MockProvider.articlesResponse
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.ConnectionManager
import info.tommarsh.mynews.core.util.NetworkHelper
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import retrofit2.Response

class ArticlesRemoteDataStoreTest {

    private val goodResponse = Response.success(articlesResponse)

    private val api = mock<ArticleApiService> {
        onBlocking { getBreakingNews("bbc,independent") }.thenReturn(goodResponse)

        onBlocking { getArticlesForCategory("category") }.thenReturn(goodResponse)

        onBlocking { searchArticles("query") }.thenReturn(goodResponse)
    }
    private val preferences = mock<PreferencesRepository> {
        on { getSources() }.thenReturn("bbc,independent")
    }
    private val connectionManager = mock<ConnectionManager> {
        on { isConnected }.thenReturn(true)
    }
    private val network = NetworkHelper(connectionManager)
    private val remoteDataStore =
        ArticlesRemoteDataStore(
            network,
            preferences,
            api
        )


    @Test
    fun `Get breaking news from network`() = runBlockingTest {
        val expected = Outcome.Success(listOf(articleModel, articleModel))

        val actual = remoteDataStore.getBreakingNews()

        assertEquals(expected, actual)
    }

    @Test
    fun `Search articles from network`() = runBlockingTest {
        val expected = Outcome.Success(listOf(articleModel, articleModel))

        val actual = remoteDataStore.getArticleForCategory("category")

        assertEquals(expected, actual)
    }

    @Test
    fun `Get articles by category`() = runBlockingTest {
        val expected = Outcome.Success(listOf(articleModel, articleModel))

        val actual = remoteDataStore.searchArticles("query")

        assertEquals(expected, actual)
    }
}