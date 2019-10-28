package info.tommarsh.mynews.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import info.tommarsh.mynews.core.NetworkException
import info.tommarsh.mynews.core.Outcome
import info.tommarsh.mynews.core.errors.ErrorLiveData
import info.tommarsh.mynews.repository.model.MockProvider.articleModel
import info.tommarsh.mynews.repository.model.MockProvider.categoryModel
import info.tommarsh.mynews.repository.model.MockProvider.noInternet
import info.tommarsh.mynews.repository.source.local.articles.ArticlesLocalDataStore
import info.tommarsh.mynews.repository.source.remote.articles.ArticlesRemoteDataStore
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ArticleDataRepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val errors = ErrorLiveData()
    private val localDataStore = mock<ArticlesLocalDataStore>()
    private val remoteDataStore = mock<ArticlesRemoteDataStore>()
    private val repository =
        ArticleDataRepository(localDataStore, remoteDataStore, errors)

    private val errorObserver = mock<Observer<NetworkException>>()

    @Before
    fun `Set up`() {
        repository.errors.observeForever(errorObserver)
    }

    @Test
    fun `Get breaking news from db and network, and persist`() = runBlocking<Unit> {
        whenever(localDataStore.getBreakingNews()).thenReturn(flowOf(listOf(articleModel)))

        repository.getBreakingNews("")

        verify(localDataStore).getBreakingNews()
    }

    @Test
    fun `Refresh breaking news, with error`() = runBlocking {
        whenever(remoteDataStore.getBreakingNews())
            .thenReturn(Outcome.Error(noInternet))

        repository.refreshBreakingNews()

        verify(remoteDataStore).getBreakingNews()
        verify(errorObserver).onChanged(noInternet)
    }

    @Test
    fun `Refresh breaking news, save to db`() = runBlocking {
        whenever(remoteDataStore.getBreakingNews()).thenReturn(Outcome.Success(listOf(articleModel, articleModel)))

        repository.refreshBreakingNews()

        verify(remoteDataStore).getBreakingNews()
        verifyNoMoreInteractions(errorObserver)
    }

    @Test
    fun `Get feed`() = runBlockingTest {
        whenever(localDataStore.getFeed()).thenReturn(flowOf(listOf(articleModel)))

        repository.getFeed()

        verify(localDataStore).getFeed()
    }

    @Test
    fun `Refresh feed, save to db`() = runBlocking {
        whenever(remoteDataStore.getArticleForCategory(any()))
            .thenReturn(Outcome.Success(listOf(articleModel, articleModel)))

        repository.refreshFeed(listOf(categoryModel, categoryModel))

        verify(remoteDataStore, times(2)).getArticleForCategory(categoryModel.id)
        verify(localDataStore, times(2)).saveCategory(
            categoryModel.id,
            listOf(articleModel, articleModel)
        )
    }

    @Test
    fun `Refresh feed, with error`() = runBlocking {
        whenever(remoteDataStore.getArticleForCategory(any()))
            .thenReturn(Outcome.Error(noInternet))

        repository.refreshFeed(listOf(categoryModel, categoryModel))

        verify(errorObserver, times(2)).onChanged(noInternet)
    }
}