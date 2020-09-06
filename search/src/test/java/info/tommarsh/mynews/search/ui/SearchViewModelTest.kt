package info.tommarsh.mynews.search.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.model.Outcome
import info.tommarsh.mynews.core.util.ErrorLiveData
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.search.model.Action
import info.tommarsh.mynews.search.model.MockModelProvider.articleModel
import info.tommarsh.mynews.search.model.MockModelProvider.articleViewModel
import info.tommarsh.mynews.search.model.MockModelProvider.noInternet
import info.tommarsh.mynews.search.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
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

    private val dispatcherProvider = mock<DispatcherProvider> {
        on { main() }.thenReturn(testCoroutineDispatcher)
        on { work() }.thenReturn(testCoroutineDispatcher)
    }

    private val timeHelper = mock<TimeHelper> {
        on { timeBetween(now = any(), isoString = any()) }.thenReturn("1 hour ago")
    }

    private val searchViewModel =
        SearchViewModel(articlesRepository, dispatcherProvider, timeHelper)
    private val uiStateObserver = mock<Observer<Event>>()


    @Before
    fun `Set up`() {
        Dispatchers.setMain(testCoroutineDispatcher)
        searchViewModel.searchState.observeForever(uiStateObserver)
    }

    @After
    fun `Tear down`() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
        searchViewModel.searchState.removeObserver(uiStateObserver)
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

        searchViewModel.postAction(Action.Search("1234"))

        verify(articlesRepository).searchArticles("1234")
        verify(uiStateObserver).onChanged(Event.Loading)
        verify(uiStateObserver).onChanged(Event.FetchedResults(listOf(articleViewModel, articleViewModel)))
    }

    @Test
    fun `show error when failing to get search results`() = runBlockingTest {

        searchViewModel.postAction(Action.Search("1234"))

        verify(uiStateObserver).onChanged(Event.Error(noInternet))
    }
}