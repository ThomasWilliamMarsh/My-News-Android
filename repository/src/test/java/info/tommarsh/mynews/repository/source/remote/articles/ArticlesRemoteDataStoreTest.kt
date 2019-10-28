package info.tommarsh.mynews.repository.source.remote.articles

import com.nhaarman.mockitokotlin2.mock
import info.tommarsh.mynews.core.Outcome
import info.tommarsh.mynews.repository.model.MockProvider.articleModel
import info.tommarsh.mynews.repository.model.MockProvider.articlesResponse
import info.tommarsh.mynews.repository.model.remote.mapper.ArticleResponseMapper
import info.tommarsh.mynews.repository.model.remote.mapper.SourceResponseMapper
import info.tommarsh.mynews.repository.util.ConnectionManager
import info.tommarsh.mynews.repository.util.NetworkHelper
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import retrofit2.Response

class ArticlesRemoteDataStoreTest {

    private val goodResponse = Response.success(articlesResponse)
    private val mapper = ArticleResponseMapper(SourceResponseMapper())
    private val api = mock<ArticleApiService> {
        onBlocking { getBreakingNews() }.thenReturn(goodResponse)

        onBlocking { getArticlesForCategory("category") }.thenReturn(goodResponse)

        onBlocking { searchArticles("query") }.thenReturn(goodResponse)
    }
    private val connectionManager = mock<ConnectionManager> {
        on { isConnected }.thenReturn(true)
    }
    private val network = NetworkHelper(connectionManager)
    private val remoteDataStore = ArticlesRemoteDataStore(mapper, network, api)


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