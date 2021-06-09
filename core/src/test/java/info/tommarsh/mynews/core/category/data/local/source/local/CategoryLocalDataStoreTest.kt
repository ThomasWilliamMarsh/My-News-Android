package info.tommarsh.mynews.core.category.data.local.source.local

import app.cash.turbine.test
import info.tommarsh.mynews.core.category.data.local.model.Category
import info.tommarsh.mynews.core.category.data.local.model.toDomainModel
import info.tommarsh.mynews.core.category.data.local.source.CategoryDao
import info.tommarsh.mynews.core.category.data.local.source.CategoryLocalDataStore
import info.tommarsh.mynews.test.UnitTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CategoryLocalDataStoreTest : UnitTest<CategoryLocalDataStore>() {
    private val dao = mock<CategoryDao>()

    private val categoriesList = fixture<List<Category>>()

    @Test
    fun `Get categories from DB`() = runBlockingTest {
        whenever(dao.getCategories()).thenReturn(flowOf(categoriesList))

        sut.getCategories().test {
            assertEquals(expectItem(), categoriesList.map { it.toDomainModel() })
            expectComplete()
        }

        verify(dao).getCategories()
    }

    @Test
    fun `Update category`() = runBlockingTest {
        val id = fixture<String>()
        val selected = fixture<Boolean>()

        sut.updateCategory(id, selected)

        verify(dao).updateCategory(id, selected)
    }

    override fun createSut(): CategoryLocalDataStore {
        return CategoryLocalDataStore(dao)
    }
}