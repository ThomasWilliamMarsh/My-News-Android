package info.tommarsh.mynews.core.category.data.local.source.local

import info.tommarsh.mynews.core.category.data.local.source.CategoryDao
import info.tommarsh.mynews.core.category.data.local.source.CategoryLocalDataStore
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CategoryLocalDataStoreTest {
    private val dao = mock<CategoryDao>()

    private val local =
        CategoryLocalDataStore(dao)

    @Test
    fun `Get categories from DB`() {
        whenever(dao.getCategories()).thenReturn(flowOf())

        local.getCategories()

        verify(dao).getCategories()
    }

    @Test
    fun `Update category`() = runBlocking {

        local.updateCategory("id", true)

        verify(dao).updateCategory("id", true)
    }
}