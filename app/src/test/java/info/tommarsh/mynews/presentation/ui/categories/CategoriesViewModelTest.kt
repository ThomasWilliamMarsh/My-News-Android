package info.tommarsh.mynews.presentation.ui.categories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import info.tommarsh.mynews.categories.model.CategoryViewModel
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.presentation.model.MockModelProvider.articleModel
import info.tommarsh.mynews.presentation.model.MockModelProvider.articleViewModel
import info.tommarsh.mynews.presentation.model.MockModelProvider.footballCategoryModel
import info.tommarsh.mynews.presentation.model.MockModelProvider.footballCategoryViewModel
import kotlinx.coroutines.Dispatchers
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
    private val dispatcherProvider = mock<DispatcherProvider> {
        on { work() }.thenReturn(testCoroutineDispatcher)
        on { main() }.thenReturn(testCoroutineDispatcher)
    }
    private val timeHelper = mock<TimeHelper>()

    private val observer = mock<Observer<List<CategoryViewModel>>>()

    private val viewModel = CategoriesViewModel(
        articleRepository,
        categoryRepository,
        dispatcherProvider,
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
    fun `Get selected categories from live data`() {
        viewModel.selectedCategories.observeForever(observer)

        verify(observer).onChanged(listOf(footballCategoryViewModel))

        viewModel.selectedCategories.removeObserver(observer)
    }

    @Test
    fun `Get articles for a category`() = testCoroutineDispatcher.runBlockingTest {

        viewModel.getArticlesForCategory("football")
            .flowOn(testCoroutineDispatcher)

        verify(articleRepository).getArticlesForCategory("football", 5)
    }
}