package info.tommarsh.presentation.ui.article.top

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.presentation.CoroutinesInstantTaskExecutorRule
import info.tommarsh.presentation.model.ArticleViewModel
import info.tommarsh.presentation.model.MockModelProvider.articleModel
import info.tommarsh.presentation.model.MockModelProvider.articleViewModel
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TopNewsViewModelTest {
    @get:Rule
    var rule: TestRule = CoroutinesInstantTaskExecutorRule()

    private val articlesRepository = mock<ArticleRepository> {
        onBlocking { getBreakingNews("") }.thenReturn(mock())
    }
    private val mapper = mock<ArticleViewModelMapper> {
        on { map(listOf(articleModel)) }.thenReturn(listOf(articleViewModel))
    }

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val dispatcherProvider = mock<DispatcherProvider> {
        on { main() }.thenReturn(testCoroutineDispatcher)
        on { work() }.thenReturn(testCoroutineDispatcher)
    }
    private val topNewsViewModel = TopNewsViewModel(articlesRepository, mapper, dispatcherProvider)

    @After
    fun `Tear down`() {
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Get breaking news`() = runBlockingTest {
        val observer = mock<Observer<List<ArticleViewModel>>>()
        val livedata = topNewsViewModel.articles

        livedata.observeForever(observer)

        verify(articlesRepository).getBreakingNews("")
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