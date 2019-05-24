package info.tommarsh.presentation.ui.article.categories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import info.tommarsh.core.coroutines.DispatcherProvider
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.domain.source.CategoryRepository
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

class CategoriesViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val articlesRepository = mock<ArticleRepository> {
        onBlocking { getFeed() }.thenReturn(mock())
    }
    private val categoryRepository = mock<CategoryRepository> {
        onBlocking { getSelectedCategories() }.thenReturn(mock())
    }
    private val mapper = mock<ArticleViewModelMapper> {
        on { map(listOf(articleModel, articleModel)) }.thenReturn(listOf(articleViewModel, articleViewModel))
    }
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val dispatcherProvider = mock<DispatcherProvider> {
        on { main() }.thenReturn(testCoroutineDispatcher)
        on { work() }.thenReturn(testCoroutineDispatcher)
    }

    private val categoryViewModel =
        CategoriesViewModel(articlesRepository, categoryRepository, mapper, dispatcherProvider)

    private val articleObserver = mock<Observer<List<ArticleViewModel>>>()

    @After
    fun `Tear down`() {
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Get selected categories`() = testCoroutineDispatcher.runBlockingTest {
        val livedata = categoryViewModel.feed

        livedata.observeForever(articleObserver)

        verify(categoryRepository, times(2)).getSelectedCategories()
        livedata.removeObserver(articleObserver)
    }

    @Test
    fun `Get categories Feed`() = testCoroutineDispatcher.runBlockingTest {

        categoryViewModel.selectedCategories

        verify(categoryRepository, times(2)).getSelectedCategories()
    }

    @Test
    fun `Refresh feed`() = testCoroutineDispatcher.runBlockingTest {

        categoryViewModel.refreshFeed()

        verify(articlesRepository).refreshFeed(emptyList())
    }
}