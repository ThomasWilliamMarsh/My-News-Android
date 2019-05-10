package info.tommarsh.presentation.ui.article.categories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.domain.source.CategoryRepository
import info.tommarsh.presentation.model.MockModelProvider.articleModel
import info.tommarsh.presentation.model.MockModelProvider.articleViewModel
import info.tommarsh.presentation.model.MockModelProvider.categoryModel
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CategoriesViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val articlesRepository = mock<ArticleRepository>()
    private val categoryRepository = mock<CategoryRepository>()
    private val mapper = mock<ArticleViewModelMapper> {
        on { map(articleModel) }.thenReturn(articleViewModel)
    }
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val dispatcherProvider = mock<DispatcherProvider> {
        on { main() }.thenReturn(testCoroutineDispatcher)
        on { work() }.thenReturn(testCoroutineDispatcher)
    }

    private val categoryViewModel =
        CategoriesViewModel(articlesRepository, categoryRepository, mapper, dispatcherProvider)


    @After
    fun `Tear down`() {
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Get selected categories`() {

        categoryViewModel.getFeed()

        verify(articlesRepository).getFeed()
    }

    @Test
    fun `Get categories Feed`() {

        categoryViewModel.getSelectedCategories()

        verify(categoryRepository).getSelectedCategories()
    }

    @Test
    fun `Refresh feed`() = testCoroutineDispatcher.runBlockingTest {

        categoryViewModel.refreshFeed(listOf(categoryModel, categoryModel))

        verify(articlesRepository).refreshFeed(listOf(categoryModel, categoryModel))
    }
}