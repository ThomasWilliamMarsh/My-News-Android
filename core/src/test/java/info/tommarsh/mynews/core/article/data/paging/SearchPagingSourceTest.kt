package info.tommarsh.mynews.core.article.data.paging

import androidx.paging.PagingSource
import info.tommarsh.mynews.core.article.data.remote.source.ArticlesRemoteDataStore
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.model.Resource
import info.tommarsh.mynews.test.UnitTest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SearchPagingSourceTest : UnitTest<SearchPagingSource>() {

    private val remoteDataSource = mock<ArticlesRemoteDataStore>()

    private val query = fixture<String>()

    @Test
    fun `Fail loading search articles`() = runBlockingTest {
        val networkException = NetworkException.ServerException()
        whenever(remoteDataSource.searchArticles(page = 1, query = query))
            .thenReturn(Resource.Error(networkException))

        val result = sut.load(
            params = PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Error)
        assertTrue((result as PagingSource.LoadResult.Error).throwable == networkException)
    }

    @Test
    fun `load search articles`() = runBlockingTest {
        val articleModelList = fixture<List<ArticleModel>>()
        whenever(remoteDataSource.searchArticles(page = 3, query = query))
            .thenReturn(Resource.Data(articleModelList))

        val result = sut.load(
            params = PagingSource.LoadParams.Refresh(
                key = 3,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Page)

        val page = result as PagingSource.LoadResult.Page
        assertTrue(page.data == articleModelList)
        assertTrue(page.prevKey == 2)
        assertTrue(page.nextKey == 4)
    }

    @Test
    fun `No next key if we have no data`() = runBlockingTest {

        whenever(remoteDataSource.searchArticles(page = 2, query = query))
            .thenReturn(Resource.Data(emptyList()))

        val result = sut.load(
            params = PagingSource.LoadParams.Refresh(
                key = 2,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        assertTrue((result as PagingSource.LoadResult.Page).nextKey == null)
    }

    @Test
    fun `No previous key if current page is 1`() = runBlockingTest {
        whenever(remoteDataSource.searchArticles(page = 1, query = query))
            .thenReturn(Resource.Data(emptyList()))

        val result = sut.load(
            params = PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        assertTrue((result as PagingSource.LoadResult.Page).prevKey == null)
    }

    override fun createSut(): SearchPagingSource {
        return SearchPagingSource(query, remoteDataSource)
    }
}