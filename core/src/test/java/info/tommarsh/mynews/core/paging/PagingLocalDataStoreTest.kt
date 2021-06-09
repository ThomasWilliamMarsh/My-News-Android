package info.tommarsh.mynews.core.paging

import info.tommarsh.mynews.core.paging.source.Page
import info.tommarsh.mynews.core.paging.source.PagingDao
import info.tommarsh.mynews.test.UnitTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PagingLocalDataStoreTest : UnitTest<PagingLocalDataStore>() {

    private val dao = mock<PagingDao>()

    private val category = fixture<String>()

    @Test
    fun `get page number for business category`() = runBlocking {
        val page = fixture<Int>()
        whenever(dao.getPageForCategory(category)).thenReturn(page)

        val actual = sut.getPageForCategory(category)

        verify(dao).getPageForCategory(category)
        assertEquals(page, actual)
    }

    @Test(expected = Exception::class)
    fun `get page 1 if no category `() = runBlocking {
        val exception = fixture<Exception>()
        whenever(dao.getPageForCategory(category)).thenThrow(exception)
        val expected = 1

        val actual = sut.getPageForCategory(category)

        assertEquals(expected, actual)
    }

    @Test
    fun `update page number`() = runBlocking {
        val page = fixture<Int>()

        sut.setPageForCategory(category, page)

        verify(dao).setPageForCategory(Page(id = category, page = page))
    }

    override fun createSut(): PagingLocalDataStore {
        return PagingLocalDataStore(dao)
    }
}