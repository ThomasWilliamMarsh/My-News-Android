package info.tommarsh.mynews.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.core.NetworkException
import info.tommarsh.mynews.core.errors.ErrorLiveData
import info.tommarsh.mynews.core.model.CategoryModel
import info.tommarsh.mynews.repository.source.local.category.CategoryLocalDataStore
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
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
        whenever(localDataStore.getSelectedCategoriesStream()).thenReturn(categoriesLiveData)

        repository.getSelectedCategoriesStream()

        verify(localDataStore).getSelectedCategoriesStream()
    }

    @Test
    fun `Update categories`() = runBlocking {

        repository.updateCategory("id", true)

        verify(localDataStore).updateCategory("id", true)
    }
}