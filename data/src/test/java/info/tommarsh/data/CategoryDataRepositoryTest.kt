package info.tommarsh.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.core.network.NetworkException
import info.tommarsh.data.model.MockProvider.categoryModel
import info.tommarsh.data.source.local.category.CategoryLocalDataStore
import info.tommarsh.domain.model.CategoryModel
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class CategoryDataRepositoryTest {

    private val errors = mock<ErrorLiveData>()
    private val localDataStore = mock<CategoryLocalDataStore>()
    private val repository = CategoryDataRepository(localDataStore, errors)

    private val errorObserver = mock<Observer<NetworkException>>()
    private val categoriesLiveData = MutableLiveData<List<CategoryModel>>()

    @Before
    fun `Set up`() {
        repository.errors.observeForever(errorObserver)
    }

    @After
    fun `Tear down`() {
        repository.errors.removeObserver(errorObserver)
    }

    @Test
    fun `Get Categories`() {
        whenever(localDataStore.getCategories()).thenReturn(categoriesLiveData)

        val actual = repository.getCategories()

        verify(localDataStore).getCategories()
        assertEquals(categoriesLiveData, actual)
    }

    @Test
    fun `Get selected categories`() {
        whenever(localDataStore.getSelectedCategories()).thenReturn(categoriesLiveData)

        repository.getSelectedCategories()

        verify(localDataStore).getSelectedCategories()
    }

    @Test
    fun `Update categories`() {

        repository.updateCategory(categoryModel)

        verify(localDataStore).updateCategory(categoryModel)
    }
}