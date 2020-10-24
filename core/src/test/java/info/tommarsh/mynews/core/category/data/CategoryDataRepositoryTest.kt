package info.tommarsh.mynews.core.category.data

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.core.category.data.local.source.CategoryLocalDataStore
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CategoryDataRepositoryTest {

    private val localDataStore = mock<CategoryLocalDataStore>()
    private val repository =
        CategoryDataRepository(localDataStore)

    @Test
    fun `Get Categories`() {
        whenever(localDataStore.getCategories()).thenReturn(flowOf())

        repository.getCategories()

        verify(localDataStore).getCategories()
    }

    @Test
    fun `Update categories`() = runBlocking {

        repository.updateCategory("id", true)

        verify(localDataStore).updateCategory("id", true)
    }
}