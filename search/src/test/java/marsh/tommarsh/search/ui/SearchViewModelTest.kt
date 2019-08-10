package marsh.tommarsh.search.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.core.NetworkException
import info.tommarsh.core.Outcome
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.core.errors.ErrorLiveData
import info.tommarsh.core.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import marsh.tommarsh.search.model.MockModelProvider.articleModel
import marsh.tommarsh.search.model.MockModelProvider.articleViewModel
import marsh.tommarsh.search.model.MockModelProvider.noInternet
import marsh.tommarsh.search.model.SearchItemViewModel
import marsh.tommarsh.search.model.mapper.SearchItemViewModelMapper
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val errorsLiveData = ErrorLiveData()
    private val articlesRepository = mock<ArticleRepository> {
        onBlocking { searchArticles("1234") }.thenReturn(Outcome.Error(noInternet))
        on { errors }.thenReturn(errorsLiveData)
    }
    private val mapper = mock<SearchItemViewModelMapper> {
        on { map(listOf(articleModel, articleModel)) }.thenReturn(listOf(articleViewModel, articleViewModel))
    }
    private val dispatcherProvider = mock<DispatcherProvider> {
        on { main() }.thenReturn(testCoroutineDispatcher)
        on { work() }.thenReturn(testCoroutineDispatcher)
    }
    private val searchViewModel = SearchViewModel(articlesRepository, mapper, dispatcherProvider)
    private val articlesObserver = mock<Observer<List<SearchItemViewModel>>>()
    private val errorObserver = mock<Observer<NetworkException>>()


    @Before
    fun `Set up`() {
        Dispatchers.setMain(testCoroutineDispatcher)
        searchViewModel.errors.observeForever(errorObserver)
        searchViewModel.articles.observeForever(articlesObserver)
    }

    @After
    fun `Tear down`() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
        searchViewModel.errors.removeObserver(errorObserver)
        searchViewModel.articles.removeObserver(articlesObserver)
    }

    @Test
    fun `Get errors`() {

        searchViewModel.errors

        verify(articlesRepository).errors
    }

    @Test
    fun `get search results`() = runBlockingTest {
        whenever(articlesRepository.searchArticles("1234")).thenReturn(
            Outcome.Success(
                listOf(
                    articleModel,
                    articleModel
                )
            )
        )

        searchViewModel.searchArticles("1234")

        verify(articlesRepository).searchArticles("1234")
        verify(articlesObserver).onChanged(listOf(articleViewModel, articleViewModel))
    }

    @Test
    fun `show error when failing to get search results`() = runBlockingTest {

        searchViewModel.searchArticles("1234")

        verify(errorObserver).onChanged(noInternet)
    }
}