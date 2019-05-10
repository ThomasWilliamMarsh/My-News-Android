package info.tommarsh.presentation.ui.article.top

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyBlocking
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.presentation.model.MockModelProvider
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TopNewsViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val articlesRepository = mock<ArticleRepository>()
    private val mapper = mock<ArticleViewModelMapper> {
        on { map(MockModelProvider.articleModel) }.thenReturn(MockModelProvider.articleViewModel)
    }
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val dispatcherProvider = mock<DispatcherProvider> {
        on { main() }.thenReturn(testCoroutineDispatcher)
        on { work() }.thenReturn(testCoroutineDispatcher)
    }
    private val topNewsViewModel = TopNewsViewModel(articlesRepository, mapper, dispatcherProvider)

    @Test
    fun `Get breaking news`() {

        topNewsViewModel.getArticlesObservable()

        verify(articlesRepository).getBreakingNews("")
    }

    @Test
    fun `Get errors`() {

        topNewsViewModel.getErrors()

        verify(articlesRepository).errors
    }

    @Test
    fun `Refresh breaking news`() = runBlocking {

        topNewsViewModel.refreshBreakingNews()

        verify(articlesRepository, times(2)).refreshBreakingNews()
    }
}