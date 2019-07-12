package info.tommarsh.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import info.tommarsh.core.NetworkException
import info.tommarsh.core.Outcome
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.data.model.MockProvider.articleModel
import info.tommarsh.data.model.MockProvider.categoryModel
import info.tommarsh.data.model.MockProvider.noInternet
import info.tommarsh.data.source.local.articles.ArticlesLocalDataStore
import info.tommarsh.data.source.remote.articles.ArticlesRemoteDataStore
import info.tommarsh.domain.model.ArticleModel
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
    private val repository = ArticleDataRepository(localDataStore, remoteDataStore, errors)

    private val feedLivedata = MutableLiveData<List<ArticleModel>>()
    private val errorObserver = mock<Observer<NetworkException>>()
    private val feedObserver = mock<Observer<List<ArticleModel>>>()

    @Before
    fun `Set up`() {
        repository.errors.observeForever(errorObserver)
    }

    @Test
    fun `Get breaking news from db and network, and persist`() = runBlocking<Unit> {
        whenever(localDataStore.getBreakingNews()).thenReturn(MutableLiveData())

        repository.getBreakingNews("").observeForever(feedObserver)

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
        whenever(localDataStore.getFeed()).thenReturn(feedLivedata)

        repository.getFeed().observeForever(feedObserver)

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