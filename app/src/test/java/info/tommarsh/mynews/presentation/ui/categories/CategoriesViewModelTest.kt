package info.tommarsh.mynews.presentation.ui.categories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import info.tommarsh.core.ViewModel
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.core.repository.ArticleRepository
import info.tommarsh.core.repository.CategoryRepository
import info.tommarsh.mynews.presentation.model.MockModelProvider.articleModel
import info.tommarsh.mynews.presentation.model.MockModelProvider.articleViewModel
import info.tommarsh.mynews.presentation.model.MockModelProvider.categoryModel
import info.tommarsh.mynews.presentation.model.mapper.ArticleViewModelMapper
import kotlinx.coroutines.Dispatchers
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
        onBlocking { getFeed() }.thenReturn(mock())
    }
    private val categoryRepository = mock<CategoryRepository> {
        onBlocking { getSelectedCategoriesStream() }.thenReturn(mock())
        onBlocking { getSelectedCategories() }.thenReturn(listOf(categoryModel, categoryModel))
    }
    private val mapper = mock<ArticleViewModelMapper> {
        on { map(listOf(articleModel, articleModel)) }.thenReturn(listOf(articleViewModel, articleViewModel))
    }

    private val dispatcherProvider = mock<DispatcherProvider> {
        on { main() }.thenReturn(testCoroutineDispatcher)
        on { work() }.thenReturn(testCoroutineDispatcher)
    }

    private val categoryViewModel =
        CategoriesViewModel(
            articlesRepository,
            categoryRepository,
            mapper,
            dispatcherProvider
        )

    private val articleObserver = mock<Observer<List<ViewModel>>>()

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
    fun `Get selected categories`() = runBlockingTest {
        val livedata = categoryViewModel.feed

        livedata.observeForever(articleObserver)

        verify(categoryRepository, times(2)).getSelectedCategoriesStream()
        livedata.removeObserver(articleObserver)
    }

    @Test
    fun `Get categories Feed`() = runBlockingTest {

        categoryViewModel.selectedCategories

        verify(categoryRepository, times(2)).getSelectedCategoriesStream()
    }

    @Test
    fun `Refresh feed`() = runBlockingTest {

        categoryViewModel.refreshFeed()

        verify(articlesRepository).refreshFeed(listOf(categoryModel, categoryModel))
    }
}