package info.tommarsh.mynews.search.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.search.model.MockModelProvider.articleModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SearchViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val timeHelper = mock<TimeHelper>()

    private val repository = mock<ArticleRepository>()

    private val viewModel = SearchViewModel(repository, timeHelper)

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
    fun `Fetches query from repository`() = runBlockingTest {
        whenever(repository.searchArticles("Dogs")).thenReturn(
            flowOf(
                PagingData.from(
                    listOf(
                        articleModel
                    )
                )
            )
        )

        viewModel.searchArticles("Dogs")

        verify(repository).searchArticles("Dogs")
    }
}