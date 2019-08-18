package info.tommarsh.mynews.repository.source.local.category

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.repository.model.MockProvider.category
import info.tommarsh.mynews.repository.model.MockProvider.categoryModel
import info.tommarsh.mynews.repository.model.local.Category
import info.tommarsh.mynews.repository.model.local.mapper.CategoryDataToDomainMapper
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CategoryLocalDataStoreTest {
    private val dao = mock<CategoryDao>()
    private val dataMapper = mock<CategoryDataToDomainMapper> {
        on { map(listOf(category)) }.thenReturn(listOf(categoryModel))
    }
    private val categoryLiveData = MutableLiveData<List<Category>>()

    private val local = CategoryLocalDataStore(dao, dataMapper)

    @Test
    fun `Get categories from DB`() {
        whenever(dao.getCategories()).thenReturn(categoryLiveData)

        local.getCategories()

        verify(dao).getCategories()
    }

    @Test
    fun `Get selected categories from DB`() {
        whenever(dao.getSelectedCategoriesStream()).thenReturn(categoryLiveData)

        local.getSelectedCategoriesStream()

        verify(dao).getSelectedCategoriesStream()
    }

    @Test
    fun `Update category`() = runBlocking {

        local.updateCategory("id", true)

        verify(dao).updateCategory("id", true)
    }
}