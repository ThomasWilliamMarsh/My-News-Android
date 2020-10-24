package info.tommarsh.mynews.presentation.ui.top

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.presentation.model.MockModelProvider.articleModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
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

    private val timeHelper = mock<TimeHelper>()

    private val repository = mock<ArticleRepository> {
        onBlocking { getArticlesForCategory("general") }.thenReturn(
            flow {
                emit(PagingData.from(listOf(articleModel)))
            }
        )
    }

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val viewModel = TopNewsViewModel(repository, timeHelper)

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
    fun `Get flow of top stories`() = testCoroutineDispatcher.runBlockingTest {

        viewModel.articles.collect()

        verify(repository).getArticlesForCategory("general")
    }
}