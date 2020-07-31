package info.tommarsh.mynews.core.category.data

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.core.category.data.local.source.CategoryLocalDataStore
import info.tommarsh.mynews.core.model.NetworkException
import info.tommarsh.mynews.core.util.ErrorLiveData
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class CategoryDataRepositoryTest {

    private val errors = mock<ErrorLiveData>()
    private val localDataStore = mock<CategoryLocalDataStore>()
    private val repository =
        CategoryDataRepository(localDataStore, errors)

    private val errorObserver = mock<Observer<NetworkException>>()

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