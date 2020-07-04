package info.tommarsh.mynews.presentation.ui.top

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.presentation.model.ArticleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TopNewsViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val articlesRepository = mock<ArticleRepository> {
        onBlocking { getBreakingNews() }.thenReturn(mock())
    }

    private val timeHelper = mock<TimeHelper> {
        on { timeBetween(now = any(), isoString = any()) }.thenReturn("1 hour ago")
    }

    private val dispatcherProvider = mock<DispatcherProvider> {
        on { main() }.thenReturn(testCoroutineDispatcher)
        on { work() }.thenReturn(testCoroutineDispatcher)
    }
    private val observer = mock<Observer<List<ArticleViewModel>>>()
    private val topNewsViewModel =
        TopNewsViewModel(articlesRepository, dispatcherProvider, timeHelper)

    @Before
    fun `Set up`() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun `Tear down`() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Get breaking news`() = runBlockingTest {
        val livedata = topNewsViewModel.articles

        livedata.observeForever(observer)

        verify(articlesRepository).getBreakingNews()
        livedata.removeObserver(observer)
    }

    @Test
    fun `Get errors`() {

        topNewsViewModel.errors

        verify(articlesRepository).errors
    }

    @Test
    fun `Refresh breaking news`() = runBlockingTest {

        topNewsViewModel.refreshBreakingNews()

        verify(articlesRepository).refreshBreakingNews()
    }
}