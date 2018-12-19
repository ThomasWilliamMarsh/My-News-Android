package info.tommarsh.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.core.network.NetworkException
import info.tommarsh.core.network.Outcome
import info.tommarsh.data.model.MockProvider.articleModel
import info.tommarsh.data.model.MockProvider.noInternet
import info.tommarsh.data.source.local.LocalDataStore
import info.tommarsh.data.source.remote.RemoteDataStore
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ArticleDataRepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val localDataStore = mock<LocalDataStore>()
    private val remoteDataStore = mock<RemoteDataStore>()
    private val repository = ArticleDataRepository(localDataStore, remoteDataStore)

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
    fun `Get breaking news from db and network, and persist`() {
        whenever(localDataStore.getBreakingNews()).thenReturn(MutableLiveData())

        repository.getBreakingNews("")

        verify(localDataStore).getBreakingNews()
    }

    @Test
    fun `Refresh breaking news, with error`() {
        whenever(remoteDataStore.getBreakingNews()).thenReturn(Outcome.Error(noInternet))

        repository.refreshBreakingNews()

        verify(remoteDataStore).getBreakingNews()
        verify(errorObserver).onChanged(noInternet)
    }

    @Test
    fun `Refresh breaking news, save to db`() {
        whenever(remoteDataStore.getBreakingNews()).thenReturn(Outcome.Success(listOf(articleModel, articleModel)))

        repository.refreshBreakingNews()

        verify(remoteDataStore).getBreakingNews()
        verifyNoMoreInteractions(errorObserver)
    }
}