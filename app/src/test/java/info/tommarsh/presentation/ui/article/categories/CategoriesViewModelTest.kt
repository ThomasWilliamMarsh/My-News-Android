package info.tommarsh.presentation.ui.article.categories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import info.tommarsh.domain.source.ArticleRepository
import info.tommarsh.domain.source.CategoryRepository
import info.tommarsh.presentation.model.MockModelProvider.articleModel
import info.tommarsh.presentation.model.MockModelProvider.articleViewModel
import info.tommarsh.presentation.model.MockModelProvider.categoryModel
import info.tommarsh.presentation.model.mapper.ArticleViewModelMapper
import kotlinx.coroutines.runBlocking
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

    private val categoryViewModel = CategoriesViewModel(articlesRepository, categoryRepository, mapper)

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
    fun `Refresh feed`() = runBlocking {

        categoryViewModel.refreshFeed(listOf(categoryModel, categoryModel)).join()

        verify(articlesRepository).refreshFeed(listOf(categoryModel, categoryModel))
    }
}