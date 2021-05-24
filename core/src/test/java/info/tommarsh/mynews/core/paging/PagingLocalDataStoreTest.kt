package info.tommarsh.mynews.core.paging

import info.tommarsh.mynews.core.paging.source.Page
import info.tommarsh.mynews.core.paging.source.PagingDao
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PagingLocalDataStoreTest {

    private val dao = mock<PagingDao>()

    private val localDataStore = PagingLocalDataStore(dao)

    @Test
    fun `get page number for business category`() = runBlocking {
        whenever(dao.getPageForCategory("business")).thenReturn(2)
        val expected = 2

        val actual = localDataStore.getPageForCategory("business")

        verify(dao).getPageForCategory("business")
        assertEquals(expected, actual)
    }

    @Test
    fun `get page 1 if no category `() = runBlocking {
        whenever(dao.getPageForCategory("business")).thenThrow(RuntimeException())
        val expected = 1

        val actual = localDataStore.getPageForCategory("business")

        assertEquals(expected, actual)
    }

    @Test
    fun `update page number`() = runBlocking {

        localDataStore.setPageForCategory("business", 2)

        verify(dao).setPageForCategory(Page(id = "business", page = 2))
    }
}