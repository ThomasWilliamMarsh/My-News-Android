package info.tommarsh.mynews.core.category.data

import app.cash.turbine.test
import info.tommarsh.mynews.core.category.data.local.source.CategoryLocalDataStore
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.test.UnitTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CategoryDataRepositoryTest : UnitTest<CategoryDataRepository>() {

    private val localDataStore = mock<CategoryLocalDataStore>()
    private val categories = fixture<List<CategoryModel>>()

    @Test
    fun `Get Categories`() = runBlockingTest {
        whenever(localDataStore.getCategories()).thenReturn(flowOf(categories))

        sut.getCategories().test {
            assertEquals(expectItem(), categories)
            expectComplete()
        }

        verify(localDataStore).getCategories()
    }

    @Test
    fun `Update categories`() = runBlockingTest {
        val id = fixture<String>()
        val selected = fixture<Boolean>()

        sut.updateCategory(id, selected)

        verify(localDataStore).updateCategory(id, selected)
    }

    override fun createSut(): CategoryDataRepository {
        return CategoryDataRepository(localDataStore)
    }
}