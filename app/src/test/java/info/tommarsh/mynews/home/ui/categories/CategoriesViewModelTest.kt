package info.tommarsh.mynews.home.ui.categories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.home.model.MockModelProvider.articleModel
import info.tommarsh.mynews.home.model.MockModelProvider.footballCategoryModel
import info.tommarsh.mynews.home.model.MockModelProvider.footballCategoryViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CategoriesViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val articleRepository = mock<ArticleRepository> {
        onBlocking { getArticlesForCategory("football", 5) }.thenReturn(
            flow {
                emit(
                    PagingData.from(
                        listOf(articleModel)
                    )
                )
            }
        )
    }
    private val categoryRepository = mock<CategoryRepository> {
        onBlocking { getSelectedCategories() }.thenReturn(flowOf(listOf(footballCategoryModel)))
    }

    private val timeHelper = mock<TimeHelper>()

    private val viewModel = CategoriesViewModel(
        articleRepository,
        categoryRepository,
        timeHelper
    )

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
    fun `Get selected categories from live data`() = runBlockingTest {

        val flow = viewModel.selectedCategories
            .flowOn(testCoroutineDispatcher)

        flow.collect { cats ->
            assertEquals(cats, listOf(footballCategoryViewModel))
        }

        verify(categoryRepository).getSelectedCategories()
    }

    @Test
    fun `Get articles for a category`() = testCoroutineDispatcher.runBlockingTest {

        viewModel.getArticlesForCategory("football")
            .flowOn(testCoroutineDispatcher)

        verify(articleRepository).getArticlesForCategory("football", 5)
    }
}