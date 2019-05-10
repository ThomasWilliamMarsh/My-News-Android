package info.tommarsh.data.source.remote.articles

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.core.network.NetworkHelper
import info.tommarsh.core.network.Outcome
import info.tommarsh.data.model.MockProvider.articleModel
import info.tommarsh.data.model.remote.mapper.ArticleResponseMapper
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ArticlesRemoteDataStoreTest {

    private val mapper = mock<ArticleResponseMapper>()
    private val network = mock<NetworkHelper>()
    private val api = mock<ArticleApiService>()
    private val remoteDataStore = ArticlesRemoteDataStore(mapper, network, api)

    @Test
    fun `Get breaking news from network`() = runBlocking {
        whenever(network.callApi(api.getBreakingNews(), mapper))
            .thenReturn(Outcome.Success(listOf(articleModel, articleModel)))

        val actual = remoteDataStore.getBreakingNews()

        verify(api, times(2)).getBreakingNews()
        assertEquals(Outcome.Success(listOf(articleModel, articleModel)), actual)
    }
}