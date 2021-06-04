package info.tommarsh.mynews.home.ui.categories

import androidx.paging.PagingData
import app.cash.turbine.test
import info.tommarsh.mynews.categories.model.CategoryViewModel
import info.tommarsh.mynews.core.article.data.ArticleRepository
import info.tommarsh.mynews.core.article.domain.model.ArticleModel
import info.tommarsh.mynews.core.category.data.CategoryRepository
import info.tommarsh.mynews.core.category.domain.CategoryModel
import info.tommarsh.mynews.home.mappers.ArticlePageMapper
import info.tommarsh.mynews.home.model.ArticleViewModel
import info.tommarsh.mynews.test.UnitTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class CategoriesViewModelTest : UnitTest<CategoriesViewModel>() {

    private val categoryModel = fixture<CategoryModel>()
    private val categoryViewModel = CategoryViewModel(
        categoryModel.id,
        categoryModel.name,
        categoryModel.selected
    )

    private val articleModels = fixture<List<ArticleModel>>()
    private val pagedArticleModels = PagingData.from(articleModels)

    private val articleViewModels = fixture<List<ArticleViewModel>>()
    private val pagedArticleViewModels = PagingData.from(articleViewModels)

    private val articleRepository = mock<ArticleRepository> {
        onBlocking { getArticlesForCategory("football", 5) }.thenReturn(
            flow { emit(pagedArticleModels) }
        )
    }
    private val categoryRepository = mock<CategoryRepository> {
        onBlocking { getSelectedCategories() }.thenReturn(flowOf(listOf(categoryModel)))
    }

    private val pageMapper = mock<ArticlePageMapper> {
        on { map(pagedArticleModels) }.thenReturn(pagedArticleViewModels)
    }

    @Test
    fun `Get selected categories from live data`() = runBlockingTest {

        sut.selectedCategories
            .test {
                assertEquals(listOf(categoryViewModel), expectItem())
                expectComplete()
            }

        verify(categoryRepository).getSelectedCategories()
    }

    @Test
    fun `Get articles for a category`() = runBlockingTest {

        sut.getArticlesForCategory("football")
            .test {
                assertEquals(expectItem(), pagedArticleViewModels)
                expectComplete()
            }

        verify(articleRepository).getArticlesForCategory("football", 5)
    }

    override fun createSut(): CategoriesViewModel {
        return CategoriesViewModel(
            articleRepository,
            pageMapper,
            categoryRepository,
        )
    }
}