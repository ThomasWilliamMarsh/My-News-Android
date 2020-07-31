package info.tommarsh.mynews.presentation.ui.categories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.presentation.model.CarouselViewModel
import info.tommarsh.mynews.presentation.model.MockModelProvider.entertainmentArticleModel
import info.tommarsh.mynews.presentation.model.MockModelProvider.entertainmentCarousel
import info.tommarsh.mynews.presentation.model.MockModelProvider.entertainmentCategoryModel
import info.tommarsh.mynews.presentation.model.MockModelProvider.footballArticleModel
import info.tommarsh.mynews.presentation.model.MockModelProvider.footballCarousel
import info.tommarsh.mynews.presentation.model.MockModelProvider.footballCategoryModel
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

class CategoriesViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val articlesRepository = mock<ArticleRepository> {
        onBlocking { getFeed() }.thenReturn(
            flowOf(
                listOf(
                    footballArticleModel,
                    entertainmentArticleModel
                )
            )
        )
    }
    private val categoryRepository = mock<CategoryRepository> {
        onBlocking { getSelectedCategories() }.thenReturn(
            listOf(
                footballCategoryModel,
                entertainmentCategoryModel
            )
        )
    }

    private val timeHelper = mock<TimeHelper> {
        on { timeBetween(now = any(), isoString = any()) }.thenReturn("1 hour ago")
    }

    private val observer = mock<Observer<List<CarouselViewModel>>>()

    private val dispatcherProvider = mock<DispatcherProvider> {
        on { main() }.thenReturn(testCoroutineDispatcher)
        on { work() }.thenReturn(testCoroutineDispatcher)
    }

    private val categoryViewModel =
        CategoriesViewModel(
            articlesRepository,
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
    fun `Gets selected categories and articles and maps to carousels when feed changes`() =
        runBlockingTest {
            val livedata = categoryViewModel.feed

            livedata.observeForever(observer)

            verify(categoryRepository).getSelectedCategories()
            verify(observer).onChanged(
                listOf(
                    footballCarousel,
                    entertainmentCarousel
                )
            )
            livedata.removeObserver(observer)
        }

    @Test
    fun `Refreshes feed`() = runBlockingTest {

        categoryViewModel.refreshFeed()

        verify(articlesRepository).refreshFeed(
            listOf(
                footballCategoryModel,
                entertainmentCategoryModel
            )
        )
    }
}